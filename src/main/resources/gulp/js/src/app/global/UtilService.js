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
}
