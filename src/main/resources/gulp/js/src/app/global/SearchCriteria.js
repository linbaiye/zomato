function SearchCriteriaBuilder() {
  var criteria = {};

  this.setPage = function(page) {
    if (page < 0) {
      throw "Invalid page number.";
    }
    criteria['from'] = page * 10;
    criteria['size'] = 10;
  }


  function addObject(result, name) {
    if (!result[name]) {
      result[name] = {};
    }
    return result;
  }

  this.addNestedMustPhraseMatch = function(path, field, query) {
    if (!path || !field || !query) {
      throw "Invalid parameters to build query.";
    }
    var matchPhrase = {};
    matchPhrase[path + "." + field] = query;
    var nestedMust = {
      "nested": {
        "path": path,
        "query": {
          "match_phrase": matchPhrase
        }
      }
    }
    if (!criteria['query']) {
      criteria['query'] = {};
      criteria['query']['bool'] = {};
    }
    criteria['query']['bool']['must'] = nestedMust;
  }


  this.addFilterTerms = function (terms) {
    if (!terms || !(terms instanceof Array)) {
      throw "Invalid parameters to build filter.";
    }
    var tmp = [];
    for (var i = 0; i < terms.length; i++) {
      if (typeof terms[i] != "object") {
        throw "Only objects are valid to build filter.";
      }
      tmp.push({"term": terms[i]});
    }
    if (!criteria['query']) {
      criteria['query'] = {};
      criteria['query']['bool'] = {};
      criteria['query']['bool']['filter'] = [];
    }
    for (var i = 0; i < tmp.length; i++) {
      criteria['query']['bool']['filter'].push(tmp[i]);
    }
  }

  this.addSources = function(sourceArray) {
    if (!sourceArray || !(sourceArray instanceof Array)) {
      throw "Invalid parameters to build filter.";
    }
    criteria['_source'] = sourceArray;
  }

  this.setParent = function(parentId, parentType) {
    if (typeof parentType != "string") {
      throw "Invalid parentType, a string is expected."
    }
    criteria = {
      'query': {
        'has_parent': {
          "parent_type": parentType,
          "query": {
            "term": {
              "_id": parentId
            }
          }
        }
      }
    }
  }

  this.setFromAndSize = function(from, size) {
    if (typeof from != "number" || from < 0
     || typeof size != "number" || size < 1 || size > 10) {
       throw "Invalid number."
    }
    criteria['from'] = from;
    criteria['size'] = size;
  }

  this.build = function() {
    return criteria;
  }
}
