function UtilService() {
  this.to2DArray = function(list, columnNumber) {
    if (!(list instanceof Array) || columnNumber <= 1) {
      throw "Invalid arg.";
    }
    var ret = [];
    var tmp = [];
    for (var i = 0, j = 0; i < list.length; i++) {
      tmp[j++] = list[i];
      if (j == columnNumber) {
        j = 0;
        ret.push(tmp);
        tmp = [];
      }
    }
    if (tmp.length > 0) {
      ret.put(tmp);
    }
    return ret;
  }

  this.moveItemToHead = function(item, array, key) {
    var newArray = array.slice(0);
    var index = -1;
    for (var i = 0; i < newArray.length; i++) {
      if (item[key] == newArray[i][key]) {
        index = i;
        break;
      }
    }
    if (index >= 0) {
      var tmp = newArray[0];
      newArray[0] = newArray[index];
      newArray[index] = tmp;
    } else {
      newArray[0] = item;
    }
    return newArray;
  }

  this.findItem = function(list, indexKey, value) {
    if (!(list instanceof Array) || !indexKey || !value) {
      return ;
    }
    for (var i = 0; i < list.length; i++) {
      if (list[i][indexKey] == value) {
        return list[i];
      }
    }
    return null;
  }
}
