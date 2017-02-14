import MySQLdb
from elasticsearch import Elasticsearch
import re


es = Elasticsearch([{'host': 'localhost', 'port': 9200}])

db = MySQLdb.connect(host='localhost', user = 'zomato', passwd = 'zomato', db = 'zomato', unix_socket='/tmp/mysql.sock')

cursor = db.cursor()

def tuple_to_array(tpl):
    tmp = []
    for t in tpl:
        tmp.append(t[0])
    return tmp

def get_categories(rest_id):
    cursor.execute("SELECT c.name FROM restaurant_categories j LEFT JOIN categories c \
        ON j.category_id = c.id WHERE j.restaurant_id = %s", (rest_id,));
    categories = cursor.fetchall()
    return tuple_to_array(categories)

def get_features(rest_id):
    cursor.execute("SELECT f.featured_type, f.featured_desc FROM restaurant_featured_types j LEFT JOIN featured_types f \
        ON j.featured_type_id = f.featured_type_id WHERE j.restaurant_id = %s", (rest_id,));
    return tuple_to_array(cursor.fetchall())

def get_cuisines(rest_id):
    cursor.execute("SELECT r.name FROM restaurant_cuisines l LEFT JOIN cuisines r \
        ON l.cuisine_id = r.cuisine_id WHERE l.restaurant_id = %s", (rest_id,));
    return tuple_to_array(cursor.fetchall())

def get_highlights(rest_id):
    cursor.execute("SELECT r.name FROM restaurant_highlights l LEFT JOIN highlights r \
        ON l.highlight_id = r.highlight_id WHERE l.restaurant_id = %s", (rest_id,));
    return tuple_to_array(cursor.fetchall())

def get_keywords(rest_id):
    cursor.execute("SELECT r.name FROM restaurant_keywords l LEFT JOIN keywords r \
        ON l.keyword_id = r.keyword_id WHERE l.restaurant_id = %s", (rest_id,));
    return tuple_to_array(cursor.fetchall())

def get_open_hours(rest_id):
    cursor.execute("SELECT l.weekday, l.start, l.end FROM restaurant_opening_times l \
        WHERE l.restaurant_id = %s", (rest_id,));
    records = cursor.fetchall()
    days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"]
    ret = {}
    for i in days:
        ret[i] = {"start-second": -1, "end-second": -1}
    for r in records:
        if r[1].seconds == r[2].seconds and r[1].seconds == 0:
            ret[r[0]] = {"end-second": 24 * 3600 + 1}
        ret[r[0]] = {"start-second": r[1].seconds, "end-second": r[2].seconds}
    return ret

def get_reviews(rest_id):
    cursor.execute("SELECT l.review_id, l.review_text, l.user_id, l.rate, l.review_time FROM reviews l \
        WHERE l.restaurant_id = %s", (rest_id,));
    records = cursor.fetchall()
    ret = []
    for i in records:
        ret.append({"review_text": i[1],
            "user_id": i[2], "rate": i[3], "review_time": i[4].strftime('%Y-%m-%d %H:%M:%S')})
    return ret


def tuple_to_dic(i):
    ret = {
        'id': i[0],
        'name': i[1]
    }
    if i[2] is not None:
        ret['phone'] = i[2]
    if i[3] is not None:
        ret['known_for'] = i[3]
    if i[4] is not None:
        ret['address'] = {}
        ret['address']['text_address'] = i[4]
        ret['address']['location'] = str(round(i[6], 8))+","+ str(round(i[5], 8))
    if i[7] is not None:
        m = re.search('.+A\$([.\d]+).*', i[7])
        ret['cost_for_2'] = m.group(1)
    if i[8] is not None:
        ret['thumb_img_url'] = i[8]
    if i[9] is not None:
        ret['img_url'] = i[9]
    return ret


def transform_for_es(rest):
    ret = {
        'name': rest['name'],
        'open_hours': rest['open_hours']
    }
    if rest['address'] is not None:
        ret['address'] = {}
        ret['address']['text_address'] = rest['address']['text']
        ret['address']['location'] = str(round(rest['address']['latitude'], 5))+","+ str(round(rest['address']['longitude'], 5))
    optional_keys = ['known_for', 'phone']
    for k in optional_keys:
        if rest[k] is not None:
            ret[k] = rest[k]
    if rest['img_url']:
        ret['img_url'] = rest['img_url']
    return ret

def avg_rate(reviews):
    if len(reviews) == 0:
        return 0
    total = 0
    for r in reviews:
        total += r['rate']
    return round(total / len(reviews), 5)


page = 0
while True:
    cursor.execute("SELECT r.restaurant_id, r.name, r.phone, r.known_for,\
    a.address, a.longitude, a.latitude, r.approx_price, r.thumb_img_url, r.img_url FROM restaurants r INNER JOIN addresses a\
    ON r.address_id = a.address_id  LIMIT %s OFFSET %s",
    (10, page * 10,))
    records = cursor.fetchall()
    if records is None or len(records) == 0:
        break
    res_list = []
    for i in records:
        rest = tuple_to_dic(i)
        rest['categories'] = get_categories(i[0])
        rest['features'] = get_features(i[0])
        rest['cuisines'] = get_cuisines(i[0])
        rest['highlights'] = get_highlights(i[0])
        rest['keywords'] = get_keywords(i[0])
        rest['open_hours'] = get_open_hours(i[0])
        rest['reviews'] = get_reviews(i[0])
        rest['avg_rate'] = avg_rate(rest['reviews'])
        es.index(doc_type='restaurant', index="zomato", id=rest['id'], body=rest)
        #es.update(doc_type='restaurant', index="zomato", id=rest['id'], body={"doc": {"avg_rate": avg_rate(rest['reviews'])}})
        #print   avg_rate(rest['reviews'])
    page = page + 1
