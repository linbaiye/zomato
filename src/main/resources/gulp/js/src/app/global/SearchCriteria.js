function SearchCriteriaBuilder() {
  var arg = {
    fields: [],
    query: "",
    "_source": [],
    page: 0,
  };

  function addObjectToArray(array, obj) {
    if (typeof obj == "string") {
      array.push(obj.trim());
    } else if (obj instanceof Array) {
      for (var i = 0; i < obj.length; i++) {
        array.push(obj[i].trim());
      }
    }
  }


  this.setPage = function(page) {
    if (page < 0) {
      throw "Invalid page number.";
    }
    arg['page'] = page;
  }

  this.addQueryKey = function(k) {
    if (k == "Cafés") {
      k = "cafe";
    }
    arg.query = arg.query + " " + k;
  }

  this.addSearchFields = function(f) {
      addObjectToArray(arg.fields, f);
  }

  this.addSource = function(s) {
    addObjectToArray(arg["_source"], s);
  }

  function isValid() {
    if (!arg['fields'].length || !arg['query'] || !arg['_source'].length) {
      return false;
    }
    return true;
  }

  this.build = function() {
    if (!isValid()) {
      throw "Invalid parameters.";
    }
    var tmp = {};
    for (var k in arg) {
      if (k == "page") {
        tmp['from'] = arg[k] * 10;
        tmp['size'] = 10;
      } else if (k == "query") {
        tmp["query"] = {
          "multi_match": {
            "query": arg[k].trim(),
            "fields": arg['fields']
          }
        }
        if (arg[k].indexOf("cafe") > 0) {
          tmp["query"]['multi_match']['analyzer'] = "tag_synonyms";
        }
      } else if (k == '_source') {
        tmp['_source'] = arg[k];
      }
    }
    return tmp;
  }
}
