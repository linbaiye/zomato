#!/bin/python

import MySQLdb





def transform_address(row):
    token = row[1].split(",");
    if token[len(token) - 1].strip().upper() == "NSW":
        token[len(token) - 1] = token[len(token) - 1].strip().upper();
    elif token[len(token) - 1].strip().upper() == "SYDNEY":
        token.append("NSW");
    for i in range(0, len(token)):
        token[i] = token[i].strip();
    print (", ".join(token), token[len(token) - 3], row[0],)
    return (", ".join(token), token[len(token) - 3], row[0],)


db = MySQLdb.connect("localhost", "zomato", "zomato", "zomato")
# prepare a cursor object using cursor() method
try:
    page = 0;
    hasRecoreds = True;
    while(hasRecoreds):
        read_cursor = db.cursor()
        read_cursor.execute("SELECT address_id, address FROM addresses LIMIT 100 OFFSET %s", (page * 100, ));
        read_cursor.close();
        hasRecoreds = False;

        for row in read_cursor:
            hasRecoreds = True;
            new_row = transform_address(row);
            write_cursor = db.cursor();
            write_cursor.execute("UPDATE addresses SET address = %s, district = %s WHERE address_id = %s", new_row);
            write_cursor.close();
        page = page + 1;
except Exception as e:
    print "error:" , e
    db.rollback();
db.commit();
db.close();
