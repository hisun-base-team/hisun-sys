/**使用方法：
 *第1步：拷贝下面的代码到需要校验的页面：
 <script src="css/DataValidate.js" language="javascript"></script>
 *第2步：
 *1）对要校验的控件添加需要的校验属性，如：<input type="text" allownull="false" allowlength="13" dtype="datetel" des="电话号码">
 *	其中allownull,maxlegnth,dtype des为需要自己添加的校验属性。
 * 	allownull取值：true—Input允许为空；false—不允许为空
 *	maxlength取值：只能数字类型，表示Input允许输入的最大长度；
 *	dtype代表Input只能输入的数据格式，取值：
 *   number-数字格式，date-日期格式，datetime-时间格式，tel-电话号码格式，email-电子邮件格式,float-小数
 *   IDCard-身份证（判断是15位或18位数字与字母的组合）
 *第3步：设置提交按钮onClick="checkFormData()"。
 */

/* alwayslater 2008-08-25
 加入全角字符与半角字符相互转换函数
 在日期类函数中加入了全角转半角功能
 */

/**
 * 是否是日期的检查(日期格式为"yyyy-mm-dd")
 * 格式：年必须输入四位数且必须在1900年以后；月日时分秒要么输入两个数字，要么输入一个数字；
 * @param dateStr 被检查的字符串
 * @return true(是日期格式"yyyy-mm-dd"); false(不是日期格式"yyyy-mm-dd")
 */
function isDate(dateStr) {
   //全角转半角
   dateStr = DBC2SBC(dateStr);
   var re = /^\d{4}-\d{1,2}-\d{1,2}$/;
   var r = dateStr.match(re);
   if (r == null) {
      return false;
   }
   else {
      var s = dateStr.split("-");
      if (s[0].substring(0,2) < 19 || s[1] > 12 || s[1] < 1 || s[2] > 31 || s[2] < 1) {
         return false;
      }
      if ((s[1] == 4 || s[1] == 6 || s[1] == 9 || s[1] == 11) && s[2] == 31) {//月小
         return false;
      }

      if (((s[0] % 4 == 0) && (s[0] % 100 != 0)) || (s[0] % 400 == 0)) { //是闰年
         if (s[1] == 2 &&  s[2] > 29) {
            return false;
         }
      }
      else {//不是闰年
         if (s[1] == 2 &&  s[2]>28) {
            return false;
         }
      }
   }
   return true;
}
// 定义String的去除两边空格的方法
String.prototype.trim = function (){
   var i = 0, j = this.length -1;
   // 过滤前面的空格
   while( i<this.length && (this.charCodeAt(i) == 32 ||this.charCodeAt(i)== 12288)) i++;
   if (i >= this.length) return "";
   // 过滤后面的空格
   while( j >= 0 && (this.charCodeAt(j) == 32 ||this.charCodeAt(j)== 12288)) j--;
   if (j < i) return "";
   return this.substring(i, j + 1);
}

// 定义String的去除两边空格的方法
// c为需要删除的字符
String.prototype.removeChar = function (c){
   if (this == null) return null;
   var s = "";
   for(var i=0; i<this.length; i++){
      var ch = this.charAt(i);
      if (ch != c){
         s += ch;
      }
   }
   return s;
}
//判断String对象是否为空
String.prototype.isEmpty=function(){
   if(this == null || this.length == 0)
      return true;
   return false;
}
/**
 * 是否是日期的检查(日期格式为"yyyy-mm-dd hh:mm")
 * 格式：年必须输入四位数且必须在1900年以后；月日时分要么输入两个数字，要么输入一个数字；
 * @param dateStr 被检查的字符串
 * @return true(是日期格式"yyyy-mm-dd hh:mm"); false(不是日期格式"yyyy-mm-dd hh:mm")
 */
function isDateHour(dateStr) {
   //全角转半角
   dateStr = DBC2SBC(dateStr);
   dateStr = combChar(dateStr, " ");
   var re = /^(\d{4})\-(\d{1,2})\-(\d{1,2}) (\d{1,2}):(\d{1,2})$/;
   var r = dateStr.match(re);
   if (r == null) {
      return false;
   }
   else {
      var str = dateStr.split(" ");
      var s = str[0].split("-");
      var strTime = str[1].split(":");
      if (s[0].substring(0,2) < 19 || s[1] > 12 || s[1] < 1 || s[2] > 31 || s[2] < 1) {
         return false;
      }
      if ((s[1] == 4 || s[1] == 6 || s[1] == 9 || s[1] == 11) && s[2] == 31) {//月小
         return false;
      }

      if (((s[0] % 4 == 0) && (s[0] % 100 != 0)) || (s[0] % 400 == 0)) { //是闰年
         if (s[1] == 2 &&  s[2] > 29) {
            return false;
         }
      }
      else {//不是闰年
         if (s[1] == 2 &&  s[2]>28) {
            return false;
         }
      }
      if (strTime[0] > 23 || strTime[1] > 59 || strTime[2] > 59 ) {
         return false;
      }
   }
   return true;
}
/**
 * 用来校验页面上所有的type=text的input、所有的textarea、select
 * @param formObject FORM对象。如果未传，则自动认为是DOCUMENT
 */
function checkFormData(formObject){
   var res;
   res = checkinput(formObject);
   if(!res){
      return false;
   }
   res = checktextarea(formObject);
   if(!res){
      return false;
   }
   return checkselect(formObject);
}
/**
 * 用来校验页面上所有的type=text的input、所有的textarea、select
 * @param formObject FORM对象。如果未传，则自动认为是DOCUMENT
 */
function checkFormDataForControl(control){
   if (control.tagName == "INPUT"){
      return checkInputForControl(control);
   }
   if (control.tagName == "TEXTAREA"){
      return checkTextareaForControl(control);
   }
   if (control.tagName == "SELECT"){
      return checkSelectForControl(control);
   }
   alert('不支持指定的控件类型（' + control.tagName + '）！');
   return false;
}

/**
 * 是否是日期的检查(日期格式为"yyyy-mm-dd hh:mm")
 * 格式：年必须输入四位数且必须在1900年以后；月日时分秒要么输入两个数字，要么输入一个数字；
 * @param dateStr 被检查的字符串
 * @return true(是日期格式"yyyy-mm-dd hh:mm:ss"); false(不是日期格式"yyyy-mm-dd hh:mm:ss")
 */
function isDateTime(dateStr) {
   //全角转半角
   dateStr = DBC2SBC(dateStr);
   dateStr = combChar(dateStr, " ");
   var re = /^(\d{4})\-(\d{1,2})\-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
   var r = dateStr.match(re);
   if (r == null) {
      return false;
   }
   else {
      var str = dateStr.split(" ");
      var s = str[0].split("-");
      var strTime = str[1].split(":");
      if (s[0].substring(0,2) < 19 || s[1] > 12 || s[1] < 1 || s[2] > 31 || s[2] < 1) {
         return false;
      }
      if ((s[1] == 4 || s[1] == 6 || s[1] == 9 || s[1] == 11) && s[2] == 31) {//月小
         return false;
      }

      if (((s[0] % 4 == 0) && (s[0] % 100 != 0)) || (s[0] % 400 == 0)) { //是闰年
         if (s[1] == 2 &&  s[2] > 29) {
            return false;
         }
      }
      else {//不是闰年
         if (s[1] == 2 &&  s[2]>28) {
            return false;
         }
      }
      if (strTime[0] > 23 || strTime[1] > 59 || strTime[2] > 59 ) {
         return false;
      }
   }
   return true;
}
/**
 * 判断日期dateStr1是否小于日期dateStr2的(日期格式为"yyyy-mm-dd")
 * 格式：年必须输入四位数且必须在1900年以后；月日要么输入两个数字，要么输入一个数字；
 * @param dateStr1 第一个字符串
 * @param dateStr2 第二个的字符串
 * @return null(dateStr1格式不对或dateStr2格式不对); true(dateStr1 < dateStr2); false(dateStr1 >= dateStr2)
 */
function isBeforeDate(dateStr1, dateStr2) {
   //全角转半角
   dateStr1 = DBC2SBC(dateStr1);
   dateStr2 = DBC2SBC(dateStr2);
   if (!isDate(dateStr1) || !isDate(dateStr1)) {
      return null;
   }
   var returnValue =  compareday(dateStr1,dateStr2);
   //alert(return);
   if(returnValue == -1)
      return true;
   return false;
}
/**
 * 判断日期dateStr1是否等于日期dateStr2的(日期格式为"yyyy-mm-dd")
 * 格式：年必须输入四位数且必须在1900年以后；月日要么输入两个数字，要么输入一个数字；
 * @param dateStr1 第一个字符串
 * @param dateStr2 第二个的字符串
 * @return null(dateStr1格式不对或dateStr2格式不对); true(dateStr1 = dateStr2); false(dateStr1 != dateStr2)
 */
function isEqualDate(dateStr1, dateStr2) {
   //全角转半角
   dateStr1 = DBC2SBC(dateStr1);
   dateStr2 = DBC2SBC(dateStr2);
   if (!isDate(dateStr1) || !isDate(dateStr1)) {
      return null;
   }
   var s1 = dateStr1.split("-");
   var s2 = dateStr2.split("-");
   if (s1[0] != s2[0]) {//年不相等
      return false;
   }
   else {//年相等
      if (s1[1].charAt(0) == '0') {
         s1[1] = "" + s1[1].charAt(1);
      }
      if (s2[1].charAt(0) == '0') {
         s2[1] = "" + s2[1].charAt(1);
      }
      if (s1[1] != s2[1]) {//月不相等
         return false;
      }
      else {//月相等
         if (s1[2].charAt(0) == '0') {
            s1[2] = "" + s1[2].charAt(1);
         }
         if (s2[2].charAt(0) == '0') {
            s2[2] = "" + s2[2].charAt(1);
         }
         if (s1[2] != s2[2]) {//日不相等
            return false;
         }
      }
   }
   return true;
}
/**
 * 判断日期dateStr1是否小于日期dateStr2的(日期格式为"yyyy-mm-dd hh:mm:ss")
 * 格式：年必须输入四位数且必须在1900年以后；月日时分秒要么输入两个数字，要么输入一个数字；
 * @param dateStr1 第一个字符串
 * @param dateStr2 第二个的字符串
 * @return null(dateStr1格式不对或dateStr2格式不对); true(dateStr1 < dateStr2); false(dateStr1 >= dateStr2)
 */
function isBeforeDateTime(dateStr1, dateStr2) {
   //全角转半角
   dateStr1 = DBC2SBC(dateStr1);
   dateStr2 = DBC2SBC(dateStr2);
   if (!isDateTime(dateStr1) || !isDateTime(dateStr1)) {
      return null;
   }
   var s1 = dateStr1.split(" ");
   var s2 = dateStr2.split(" ");
   if (isBeforeDate(s1[0], s2[0])) {//年月日小于
      return true;
   }
   else if (isEqualDate(s1[0], s2[0])) {//年月日相等
      var strTime1 = s1[1].split(":");
      var strTime2 = s2[1].split(":");
      if (strTime1[0].charAt(0) == '0') {
         strTime1[0] = "" + strTime1[0].charAt(1);
      }
      if (strTime2[0].charAt(0) == '0') {
         strTime2[0] = "" + strTime2[0].charAt(1);
      }
      if (strTime1[0] < strTime2[0]) {//小时小于
         return true;
      }
      else if (strTime1[0] == strTime2[0]) {//小时相等
         if (strTime1[1].charAt(0) == '0') {
            strTime1[1] = "" + strTime1[1].charAt(1);
         }
         if (strTime2[1].charAt(0) == '0') {
            strTime2[1] = "" + strTime2[1].charAt(1);
         }
         if (strTime1[1] < strTime2[1]) {//分小于
            return true;
         }
         else if (strTime1[1] == strTime2[1]) {//分相等
            if (strTime1[2].charAt(0) == '0') {
               strTime1[2] = "" + strTime1[2].charAt(1);
            }
            if (strTime2[2].charAt(0) == '0') {
               strTime2[2] = "" + strTime2[2].charAt(1);
            }
            if (strTime1[2] < strTime2[2]) {//秒小于
               return true;
            }
         }
      }
   }
   return false;
}
/**
 * 判断日期dateStr1是否等于日期dateStr2的(日期格式为"yyyy-mm-dd hh:mm:ss")
 * 格式：年必须输入四位数且必须在1900年以后；月日时分秒要么输入两个数字，要么输入一个数字；
 * @param dateStr1 第一个字符串
 * @param dateStr2 第二个的字符串
 * @return null(dateStr1格式不对或dateStr2格式不对); true(dateStr1 = dateStr2); false(dateStr1 != dateStr2)
 */
function isEqualDateTime(dateStr1, dateStr2) {
   //全角转半角
   dateStr1 = DBC2SBC(dateStr1);
   dateStr2 = DBC2SBC(dateStr2);
   if (!isDateTime(dateStr1) || !isDateTime(dateStr1)) {
      return null;
   }
   var s1 = dateStr1.split(" ");
   var s2 = dateStr2.split(" ");
   if (isEqualDate(s1[0], s2[0])) {//年月日相等
      var strTime1 = s1[1].split(":");
      var strTime2 = s2[1].split(":");
      if (strTime1[0].charAt(0) == '0') {
         strTime1[0] = "" + strTime1[0].charAt(1);
      }
      if (strTime2[0].charAt(0) == '0') {
         strTime2[0] = "" + strTime2[0].charAt(1);
      }
      if (strTime1[0] == strTime2[0]) {//小时相等
         if (strTime1[1].charAt(0) == '0') {
            strTime1[1] = "" + strTime1[1].charAt(1);
         }
         if (strTime2[1].charAt(0) == '0') {
            strTime2[1] = "" + strTime2[1].charAt(1);
         }
         if (strTime1[1] == strTime2[1]) {//分相等
            if (strTime1[2].charAt(0) == '0') {
               strTime1[2] = "" + strTime1[2].charAt(1);
            }
            if (strTime2[2].charAt(0) == '0') {
               strTime2[2] = "" + strTime2[2].charAt(1);
            }
            if (strTime1[2] == strTime2[2]) {//秒相等
               return true;
            }
         }
      }
   }
   return false;
}
/**
 * 检测是否是数字（正浮点型）
 * @param str 被检查的字符串
 * @return true; false
 */
/*function isFloat(str) {
 var Letters = "0123456789";
 var dotNum = 0;
 for (i = 0; i < str.length; i++) {
 var checkChar = str.charAt(i);
 if (Letters.indexOf(checkChar) == -1) {
 if (checkChar == '.') {
 dotNum++;
 if (dotNum > 1) {
 return false;
 }
 break;
 }
 return false;
 }

 }
 return true;
 }
 */
/**
 * 检测是否是浮点数
 * @param str 被检查的字符串
 * @return true; false
 * modify by 易洪宇 加入对负浮点型的校验
 */
function isFloat(str) {
   var Letters = "0123456789";
   var Letters2 = "-0123456789";
   var dotNum = 0;

   //对首位进行附加判断
   if(Letters2.indexOf(str.charAt(0)) == -1){
      return false;
   }else{
      for (i = 0; i < str.length; i++) {
         var checkChar = str.charAt(i);
         if (Letters.indexOf(checkChar) == -1) {
            if (checkChar == '.') {
               dotNum++;
               if (dotNum > 1) {
                  return false;
               }
               break;
            }
            return false;
         }
      }
      return true;
   }
}


/**
 * 检测是否是数字（正整型）
 * @param str 被检查的字符串
 * @return true; false
 */
/*function isNumber(str) {
 var Letters = "0123456789";
 if(str.length==0)
 return false

 for (i = 0; i < str.length; i++) {

 var checkChar = str.charAt(i);
 if (Letters.indexOf(checkChar) == -1)
 return false;
 }
 return true;
 }
 */

/**
 * 检测是否是整数
 * @param str 被检查的字符串
 * @return true; false
 * modify by 易洪宇 加入对负整型的校验
 */
function isNumber(str) {
   var Letters = "0123456789";
   var Letters2 = "-0123456789";
   if(str.length==0)
      return false;

   //对首位进行附加判断
   if(Letters2.indexOf(str.charAt(0)) == -1){
      return false;
   }else{
      for (i = 1; i < str.length; i++) {
         var checkChar = str.charAt(i);
         if (Letters.indexOf(checkChar) == -1)
            return false;
      }
      return true;
   }
}
//2004年
//校验输入是否是电话号码正确！
function isTel(telStr)
{
   var No="0123456789()+-";
   if(telStr.length==0)
      return false;

   for(i=0;i<telStr.length;i++)
   {
      var Checkstr=telStr.charAt(i);
      if(No.indexOf(Checkstr)==-1)
         return false;
   }
   return true;
}

//校验register.email是否正确
function isEmail(strEmail)
{
   if(strEmail.length=0)
      return false;
   if(strEmail.charAt(0)=="."||
       strEmail.charAt(0)=="@"||
       strEmail.indexOf('@',0)==-1||
       strEmail.indexOf('.',0)==-1||
       strEmail.lastIndexOf('@')==
       strEmail.length-1 ||
       strEmail.lastIndexOf('.')==
       strEmail.length-1 )
      return false;
   return true;
}

/**
 * 检测是否是英文字母
 * @param str 被检查的字符串
 * @return true; false
 */
function isEnglish(str) {
   var Letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
   for (i = 0; i < str.length; i++) {
      var checkChar = str.charAt(i);
      if (Letters.indexOf(checkChar) == -1) {
         return false;
      }
   }
   return true;
}
/**
 * 检测是否是合法的名字（字母，数字，下划线，且第一个字符不能为数字）
 * @param str 被检查的字符串
 * @return true; false
 */
function isValidName(str) {
   var Letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_";
   for (i = 0; i < str.length; i++) {
      var checkChar = str.charAt(i);
      if (Letters.indexOf(checkChar) == -1) {
         return true;
      }
      if (i == 0 && isNumber(checkChar)) {
         return false;
      }
   }
   return false;
}
/**
 * 判断字符长度
 * @param str 被检查的字符串
 * @return 检查字符串的长度
 */
function strLength(str) {
   var len = str.length;
   for (i = 0; i < len; i++) {
      // 判断是否为中文，如果是中文，则再增加一个字节的长度
      if (str.charCodeAt(i) < 0 || str.charCodeAt(i) > 255) {
         len++;
      }
   }
   return len;
}
/**
 * 判断字符串是否为空
 * @param str 被检查的字符串
 * @return true;false
 */
function isNull(str) {
   if (str == null || str == "") {
      return true;
   }
   else {
      return false;
   }
}
/**
 * 除去字符串前后的空字符("\r\f\t \n")
 * @param str 被检查的字符串
 * @return true;false
 */
function trim(str) {
   return str.replace(/(^\s*)|(\s*$)/g, "");
}
/**
 * 合并紧挨着的相同的字符
 * @param str 被合并的字符串
 * @param strChar 字符
 * @return 完成合并后的字符串
 */
function combChar(str, strChar) {
   if (strChar == null || strChar == "") {
      return str;
   }
   var len = str.length;
   var index = 0;//上次匹配的地方
   var isFirst = "false";//第一个字符开始的子串是否与strChar匹配
   for (i = 0; i < len; i++) {
      if (strChar.length > (len - i)) {
         break;
      }
      var strTemp = str.substring(i, i + strChar.length);
      if (strTemp == strChar) {
         if (i == 0) {
            isFirst = "true";
         }
         //如果这次匹配与上次匹配是连续匹配
         if (index == (i - strChar.length)) {
            //如果上次匹配的地方不为0，或者上次匹配的地方是第一个字符，才是连续匹配
            if (index != 0 || isFirst == "true") {
               str = str.substring(0,index) + str.substring(index + strChar.length);
               len = len - strChar.length;
               i = i - strChar.length;
            }
         }
         index = i;
         i = i + strChar.length - 1;
      }
      else {
         index = 0;
      }
   }
   return str;
}
//alert(combChar("2004200420042004aaaa","2004"));

/*
 * 判断指定INPUT对象是否符合有效性校验要求。
 */
function checkInputForControl(oobj){
   var type = oobj.type;
   if(!(type=="text"||type=="password")){ // 仅处理单行文本框及口令框
      return true;
   }
   // 取当前文本框的说明，在报错信息中，用于说明此文本框对应的信息项名称
   des = oobj.des;
   if(des==null){ // 进行有效性处理
      des="";
   }
   // 取当前文本框的值，为进行判断做准备
   value=oobj.value;
   if(value==null){
      value="";
   }
   // 检查是否允许为空
   allownull = oobj.allownull;
   if(allownull != null){
      if(allownull=="false"){
         if(value==""){
            alert("\""+des+"\"不能为空!")
            try{
               oobj.focus();
            }catch(e){}
            return false;
         }
      }
   }
   // 类型检查
   dtype = oobj.dtype;
   if(dtype!=null && value!=""){
      res = true;
      msg="";
      if(dtype == "number"){
         msg = "\""+des+"\"输入格式应该为整数格式！\r\n"; // 准备报错信息
         res=isNumber(value);
      }else if(dtype == "float"){
         msg = "\""+des+"\"输入格式应该为小数格式！\r\n";
         res = isFloat(value);
      }else if(dtype=="date"){
         msg = des + "输入格式应该为日期格式！\r\n例如:" + oobj.format;
         res = isDateWithMultiFormat(value, oobj.format, des); // 支持多种格式的日期
         if(!res) { // 校验未成功，未报过错误信息，不必再报错
            msg = "";
         }else{ // 校验成功，继续下来的判断
            //将时间转化成标准字符换后再同系统时间做比较
            dateStr = getStandardDate(value);
            var baseTime = oobj.baseTime;
            if(baseTime == null || baseTime =="") baseTime = getTodayString();
            var special = oobj.special;
            if(special != null && dateStr != null){
               if(special == "before") {
                  if(dateStr >= baseTime) {
                     res = false;
                     msg = "\"" + des + "\"必须小于" + baseTime + "！";
                  }
               }else if(special == "after"){
                  if(dateStr <= baseTime) {
                     res = false;
                     msg = "\"" + des + "\"必须大于" + baseTime + "！";
                  }
               }else if(special == "beforeAndEqual"){
                  if(dateStr > baseTime) {
                     res = false;
                     msg = "\"" + des + "\"不能大于" + baseTime + "！";
                  }
               }else if(special == "afterAndEqual"){
                  if(dateStr < baseTime) {
                     res = false;
                     msg = "\"" + des + "\"不能小于" + baseTime + "！";
                  }
               }else{
                  res = false;
                  msg = "\"" + des + "\"的“specail”参数配置（" + special + "）有错！\r\n仅支持：before、after、beforeAndEqual、afterAndEqual";
               }
            }
         }
      }else if(dtype=="datetime"){
         msg = "\"" + des + "\"输入格式应该为时间格式！\r\n例如:2004-10-09 11:30";
         res = isDateHour(value);
      }else if(dtype=="tel"){
         msg = "\"" + des + "\"输入格式应该为电话号码格式！\r\n例如:028-85169412";
         res=isTel(value);
      }else if(dtype == "email"){
         msg = "\"" + des + "\"输入格式应该为电子邮件格式！\r\n例如:xiaoxuesong@30san.com";
         res = isEmail(value);
      }else if(dtype=="IDCard"){
         msg = "\"" + des + "\"输入格式应该为合法的身份证号码！\r\n例如:310001200807150015";
         res = isIDCard(value);
      }
      if(!res){ // 判断是否有错
         if( msg !=null  && msg != ""){
            alert(msg); // 显示错误信息
         }
         try{
            oobj.focus();
            oobj.select();
         }catch(e){}
         return false;
      }
   }
   // 数据长度判断
   allowlength = oobj.allowlength;
   if(allowlength!=null){
      allowlen = parseInt(allowlength, 10);
      len = strLength(value); // 取当前数据长度，字节数（汉字算两个字节）
      if(!isNaN(allowlen)){
         if(allowlen < len){
            alert("\""+des+"\"输入长度最多只能为" + Div(allowlength,2) + "个汉字或"+allowlength+"个字符!");
            try{
               oobj.focus();
            }catch(e){}
            return false;
         }
      }
   }
   return true;
}
/**
 * 用来校验页面上所有的type=text的input类型
 */
function checkinput(formObject){
   var obj;
   if (formObject != null){
      obj = formObject.getElementsByTagName("input");
   }else{
      obj = document.getElementsByTagName("input");
   }
   // 循环处理所有INPUT控件
   for(j=0; j<obj.length; j++){
      oobj = obj(j);
      var result = checkInputForControl(oobj);
      if (result == false){
         return false;
      }
   }//end for
   return true;
}
/*
 * 针对指定多行文本框控件进行有效性校验。
 */
function checkTextareaForControl(oobj){
   des = oobj.des;
   if(des == null){
      des = "";
   }
   value = oobj.value;
   if(value == null){
      value = "";
   }
   //check null
   allownull = oobj.allownull;
   if(allownull != null){
      if(allownull == "false"){
         if(value==""){
            alert("\""+des+"\"不能为空!");
            try{
               oobj.focus();
            }catch(e){}
            return false;
         }
      }
   }
   //check length
   allowlength = oobj.allowlength;
   if(allowlength != null){
      allowlen = parseInt(allowlength,10);
      len = strLength(value);
      if(!isNaN(allowlen)){
         if(allowlen < len){
            alert("\""+des+"\"输入长度最多只能为" + Div(allowlength,2) + "个汉字或"+allowlength+"个字符!");
            try{
               oobj.focus();
            }catch(e){}
            return false;
         }
      }
   }
   return true;
}

/**
 * 用来校验页面上所有的textarea
 */
function checktextarea(formObject){
   var obj;
   if (formObject != null){
      obj = formObject.getElementsByTagName("textarea");
   }else{
      obj = document.getElementsByTagName("textarea");
   }
   for(jj=0; jj<obj.length; jj++){
      oobj = obj(jj);
      var result = checkTextareaForControl(oobj);
      if (result == false){
         return false;
      }
   }//end for
   return true;
}
/*
 * 针对指定的下拉控件进行有效性验证
 */
function checkSelectForControl(oobj){
   des = oobj.des;
   if(des == null){
      des = "";
   }
   value = "";
   //this.options[this.selectedIndex].value
   try{ // select控件的取值操作可能会出错。（大概是无数据时会出错）
      value = oobj.options[oobj.selectedIndex].value;
   }catch(e){
   }
   if(value == null){
      value = "";
   }
   //check null
   allownull = oobj.allownull;
   if(allownull != null){
      if(allownull == "false"){
         if(value == ""){
            alert("\""+des+"\"不能为空!");
            try{
               oobj.focus();
            }catch(e){}
            return false;
         }
      }
   }
   return true;
}
/**
 * 用来校验页面上所有的select
 */
function checkselect(formObject){
   var obj;
   if (formObject != null){
      obj = formObject.getElementsByTagName("select");
   }else{
      obj = document.getElementsByTagName("select");
   }
   for(i=0; i<obj.length; i++){
      oobj = obj(i);
      var result = checkSelectForControl(oobj);
      if (result == false){
         return false;
      }
   }//end for
   return true;
}

//整除
function Div(exp1, exp2)
{
   var n1 = Math.round(exp1); //四舍五入
   var n2 = Math.round(exp2); //四舍五入

   var rslt = n1 / n2; //除

   if (rslt >= 0)
   {
      rslt = Math.floor(rslt); //返回值为小于等于其数值参数的最大整数值。
   }
   else
   {
      rslt = Math.ceil(rslt); //返回值为大于等于其数字参数的最小整数。
   }

   return rslt;
}

//比较参数时间的逻辑关系。
//王耀
//参数;dateStr1,dateStr2
//2005-4-14
//dateStr1>dateStr2返回1,dateStr1<dateStr2返回-1，dateStr1=dateStr2返回0
function compareday(dateStr1,dateStr2)
{
   //全角转半角
   dateStr1 = DBC2SBC(dateStr1);
   dateStr2 = DBC2SBC(dateStr2);
   var valSTs = dateStr1.split("-");
   var valsEN = dateStr2.split("-");
   if(parseInt(valSTs[0],10) > parseInt(valsEN[0],10))
   {
      return 1;
   }
   else if(parseInt(valSTs[0],10) == parseInt(valsEN[0],10))
   {
      if(parseInt(valSTs[1],10) > parseInt(valsEN[1],10))
      {
         return 1;
      }
      else if(parseInt(valSTs[1],10) == parseInt(valsEN[1],10))
      {
         if(parseInt(valSTs[2],10) > parseInt(valsEN[2],10))
         {
            return 1;
         }
         else if(parseInt(valSTs[2],10) == parseInt(valsEN[2],10))
         {
            return 0;
         }
      }
   }
   return -1;
}

//比较参数时间是否超过当前时间。
//王耀
//参数;dateStr 被比较时间.
//2005-3-18
//>now返回1,<now返回-1，=now返回0
function compareTotay(dateStr)
{
   //全角转半角
   dateStr = DBC2SBC(dateStr);
   var d,month,day,year,val,vals;
   d = new Date();
   day = d.getDate();
   month = d.getMonth() + 1;
   year = d.getYear();

   val = dateStr;
   //alert(dateStr);
   vals = val.split("-");

   if(parseInt(vals[0],10) > parseInt(year,10))
   {
      //alert("到款日期不应超过当前日期。"+year+"-"+month+"-"+day);
      return 1;
   }
   else if(parseInt(vals[0],10) == parseInt(year,10))
   {
      if(parseInt(vals[1],10) > parseInt(month,10))
      {
         //alert("到款日期不应超过当前日期。"+year+"-"+month+"-"+day);
         return 1;
      }
      else if(parseInt(vals[1],10) == parseInt(month,10))
      {
         if(parseInt(vals[2],10) > parseInt(day,10))
         {
            //alert("到款日期不应超过当前日期。"+year+"-"+month+"-"+day);
            return 1;
         }
         else if(parseInt(vals[2],10) == parseInt(day,10))
         {
            return 0;
         }
      }
   }
   return -1;
}



/**
 * 用来校验页面上所有的type=text的input和所有的textarea
 */

function checknormaldata()
{

   res=checkinput()
   if(!res)
      return false

   res=checktextarea()
   if(!res)
      return false

   return	checkselect()

}
function isLeapYear(year)
{
   if((year%4==0&&year%100!=0)||(year%400==0))
   {
      return true;
   }
   return false;
}
//用来校验为8位的日期格式
function isSampleDate(obj){
   var datetime;
   var year,month,day;
   var gone,gtwo;

   datetime=trim(obj.value);
   if(datetime=="")
      return true;
   if(datetime!=""){
      if(datetime.length==8){
         year=datetime.substring(0,4);

         if(isNaN(year)==true){
            alert("请输入正确的日期!格式为(yyyymmdd) 例(20010101)！");
            try{
               obj.focus();
            }catch(e){}
            return false;
         }
         month=datetime.substring(4,6);
         if(isNaN(month)==true){
            alert("请输入正确的日期!格式为(yyyymmdd) 例(20010101)！");
            try{
               obj.focus();
            }catch(e){}
            return false;
         }
         day=datetime.substring(6,8);
         if(isNaN(day)==true){
            alert("请输入正确的日期!格式为(yyyymmdd) 例(20010101)！");
            try{
               obj.focus();
            }catch(e){}
            return false;
         }
         if(month<1||month>12) {
            alert("月份必须在01和12之间!");
            try{
               obj.focus();
            }catch(e){}
            return false;
         }
         if(day<1||day>31){
            alert("日期必须在01和31之间!");
            try{
               obj.focus();
            }catch(e){}
            return false;
         }else{
            if(month==2){
               if(isLeapYear(year)&&day>29){
                  alert("二月份日期必须在01到29之间!");
                  try{
                     obj.focus();
                  }catch(e){}
                  return false;
               }
               if(!isLeapYear(year)&&day>28){
                  alert("二月份日期必须在01到28之间!");
                  try{
                     obj.focus();
                  }catch(e){}
                  return false;
               }
            }
            if((month==4||month==6||month==9||month==11)&&(day>30)){
               alert("在四，六，九，十一月份 \n日期必须在01到30之间!");
               try{
                  obj.focus();
               }catch(e){}
               return false;
            }
         }
      }else{
         alert("请输入正确的日期!格式为(yyyymmdd) 例(20010101)！");
         try{
            obj.focus();
         }catch(e){}
         return false;
      }
   }else{
      alert("请输入正确的日期!格式为(yyyymmdd) 例(20010101)！");
      try{
         obj.focus();
      }catch(e){}
      return false;
   }
   return true;
}
function check_all(obj)
{
   var input = document.all.tags("input");
   for(var i=0;i<input.length;i++)
   {
      var inputName = input[i].TRANSINFOID;
      if(input[i].type == "checkbox")
      {
         input[i].checked = obj.checked;
      }
   }
}

//得到上传的文件名  zhoulan
function getFileName(title){
   var s, ss;                // 声明变量。
   var i = title.lastIndexOf(".");
   ss = title.substr(0, i);  // 获取子字符串
   var j = ss.lastIndexOf("\\");
   s = ss.substr(j+1);
   return s;
}
//判断上传的文件类型是否符合要求  zhoulan
function getFileType(title){
   if(title!=""){
      var ss;                // 声明变量。
      var i = title.lastIndexOf(".");
      ss = title.substr(i+1);  // 获取子字符串
      if(ss!="doc" && ss != "pdf" && ss!="xls"){
         alert("上传文件的格式不符合，只能上传Word,Excel,pdf格式的文件！");
         return false;
      }
   }
   return true;
}
//得到上传的文件类型  zhoulan
function checkFileDocType(title){
   if(title!=""){
      var ss;                // 声明变量。
      var i = title.lastIndexOf(".");
      ss = title.substr(i+1);  // 获取子字符串
      if(ss!="doc"){
         alert("上传文件的格式不符合，只能修改Word格式的文件！");
         return false;
      }
   }
   return true;
}
//得到上传的文件后缀  zhoulan
function returnFileType(title){
   if(title!=""){
      var ss;                // 声明变量。
      var i = title.lastIndexOf(".");
      ss = title.substr(i+1);  // 获取子字符串
      return ss;
   }
}
//得到上传的文件名及后缀  zhoulan
function getFileNameAndType(title){
   var file = getFileName(title);
   var type = returnFileType(title);
   return file+type;
}
/**
 * 向SELECT标签中增加OPTION选项 zhoulan
 * e      SELECT标签的DOM对象
 * ss     一个字符串数组，每个字符串对应一个要增加的OPTION选项，格式为value:text
 */
function addOptions(e, ss) {
   for (var i = 0; i < ss.length; i++) {
      var o = document.createElement("OPTION");
      e.options.add(o);
      var ss1 = ss[i].split(":");
      o.innerText = ss1[1];
      o.value = ss1[0];
   }
}

/**
 * 更新SELECT标签中的OPTION选项，原来的选项被删除。 zhoulan

 */
function updateOptions(e, ss) {
   if(typeof(e.options)!="undefined"){
      for (var i = e.options.length; i >= 0; i--) {
         e.options.remove(i);
      }
      addOptions(e, ss);
   }
}

/**
 * 工具函数,根据给定的源字符串和符号字符串,判断
 * 字符串中是否含有符号字符串中的符号.
 * 如果包含,则返回该符号.
 * 否则返回null
 * @param datesrc源字符串
 * @param signal符号字符串
 */
function getSplitChar(datesrc,signal){
   if(datesrc ==null || signal == null)
      return null;
   for(i = 0; i < signal.length; i++){
      if(datesrc.indexOf(signal.charAt(i)) > -1)
         return signal.charAt(i);
   }
   return null;
}

/**
 * 工具函数,根据给定的源字符串和符号字符串,判断
 * 字符串中是否含有符号字符串中的符号.
 * 如果包含,则返回该符号.
 * 否则返回null
 * @param datesrc源字符串
 * @param signal符号字符串
 */
function isHaveSplitChar(datesrc,signal){
   if(datesrc ==null || signal == null)
      return false;
   for(i = 0; i < signal.length; i++){
      if(datesrc.indexOf(signal.charAt(i)) > -1)
         return true;
   }
   return false;
}

/**
 *	工具函数，根据输入的日期字符串将日期
 *  转换为标准格式yyyymmdd或yyyymm
 */
function getStandardDate(dateStr){
   //全角转半角
   dateStr = DBC2SBC(dateStr);
   if(dateStr == null || dateStr =="") return null;
   var dateStrSplit = null;
   //对用'-'号进行分割的日期进行处理
   dateStrSplit = getSplitChar(dateStr,"-./");
   //处理有分割符的情况
   if(dateStrSplit != null){
      dateStrSplitArr = dateStr.split(dateStrSplit);
      var year = dateStrSplitArr[0]; //年
      var month = dateStrSplitArr[1];　//月
      var day = "";
      if(year.length == 2){
         if(year <= 20) year="20"+year;
         else year="19"+year;
      }
      if(month.length < 2) month = "0"+ month;//补足月位数为２位
      if(dateStrSplitArr.length > 2){
         day =  dateStrSplitArr[2];
         if(day.length <2 )   day = "0" + day;
      }
      return year+month+day;
      //处理无分隔符的情况
   }else{
      //处理字符串为yymm或者yyyymm或者yyyymmdd的情况,直接返回
      if(dateStr.length !=4 && dateStr.length !=6 && dateStr.length !=8)	return null;
      return dateStr;
   }

}

/**
 *	根据输入的年下限，上限，判断年份是否合法
 *  @param currentYear 当前年
 *  @param beginYear   下限年
 *  @param endYear     上限年
 */
function isYear(currentYear, beginYear, endYear){
   //全角转半角
   currentYear = DBC2SBC(currentYear);
   beginYear = DBC2SBC(beginYear);
   endYear = DBC2SBC(endYear);
   //判断是否为空
   if(currentYear.isEmpty() || beginYear.isEmpty.isEmpty() || endYear.isEmpty())
      return false;
   //判断输入的参数时候为数字
   if(!isNumber(currentYear) || !isNumber(beginYear) || !isNumber(endYear))
      return false;
   //判断年份范围
   if(currentYear <= endYear && currentYear >= beginYear )
      return true;
   return false;
}

/**
 *  判断输入的年份是否有效
 *  @param dateStr 输入的年份
 */
function isYear(currentYear){
   //全角转半角
   currentYear = DBC2SBC(currentYear);
   return isYear(currentYear, "1000", "3000");
}


/**
 * 工具函数,判断输入输出是否一致,如果输入是yyyymm,而要求输出
 * yyyymmdd格式,则认为不合法,但是仍然支持输入yyyymmdd,而输出
 * 为yyyymm格式.
 * @param 输入日期字符串
 * @param outputDateStr 输出格式字符串
 */
function isLegalInputAndOutput(inputDateStr,outputDateStr){
   //全角转半角
   inputDateStr = DBC2SBC(inputDateStr);
   outputDateStr = DBC2SBC(outputDateStr);
   //是否含有分割符
   var inputHaveBool = isHaveSplitChar(inputDateStr,"-./");
   var outputHaveBool = isHaveSplitChar(outputDateStr,"-./");
   var splitChar;

   //输入输出字符串中都有分割符
   if(inputHaveBool && outputHaveBool){
      var inputSplitChar= getSplitChar(inputDateStr,"-./");
      var outputSplitChar= getSplitChar(outputDateStr,"-./");
      var inputParaLength = inputDateStr.split(inputSplitChar).length;
      var outputParaLength = outputDateStr.split(outputSplitChar).length;
      if(inputParaLength == 3) return true;
      else if(inputParaLength == 2){
         //输入只有年月,而输出要求有年月日,非法
         if(outputParaLength == 3) return false;
         else return true;
      }
      return false;
      //输入有分割符,输出无分割符的情况
   }else if(inputHaveBool && !outputHaveBool){
      inputSplitChar= getSplitChar(inputDateStr,"-/.");
      inputParaLength = inputDateStr.split(inputSplitChar).length;
      if(inputParaLength == 3) return true;
      else if(inputParaLength == 2){
         //输入只有年月,而输出要求有年月日,非法
         if(outputDateStr.length == 8) return false;
         else return true;
      }
      return false;
      //输入无分割符,输出有分割符的情况
   }else if(!inputHaveBool && outputHaveBool){

      var outputSplitChar= getSplitChar(outputDateStr,"-/.");
      var outputParaLength = outputDateStr.split(outputSplitChar).length;
      if(inputDateStr.length == 8) return true;
      else if(inputDateStr.length == 6 || inputDateStr.length == 4 ){

         //输入只有年月,而输出要求有年月日,非法
         if(outputParaLength == 3) return false;
         else return true;
      }
      return false;
      //输入,输出都无分割符的情况
   }else if(!inputHaveBool && !outputHaveBool){
      if(inputDateStr.length == 8) return true;
      else if(inputDateStr.length == 6 || inputDateStr.length == 4 ){
         //输入只有年月,而输出要求有年月日,非法
         if(outputDateStr.length == 8) return false;
         else return true;
      }
      return false;
   }
   return false;
}
/**
 * 工具函数,根据年,月,日判断输入日期是否合法
 * @ year  年
 * @ month 月
 * @ day 日
 * @ return　如日期合法，返回true ,否则返回false;
 */
function isLegalDate(year,month,day){
   //全角转半角
   year = DBC2SBC(year);
   month = DBC2SBC(month);
   day = DBC2SBC(day);
   if(year.isEmpty() || month.isEmpty() || day.isEmpty() )
      return false;
   if (year.substring(0,2) < 19 || month > 12 || month < 1 || day > 31 || day < 1) {
      return false;
   }
   //判断大小月是否符合标准
   if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {//月小
      return false;
   }
   //对闰年，平年进行处理
   if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) { //是闰年
      if (month == 2 &&  day > 29) {
         return false;
      }
   }else {//不是闰年
      if (month == 2 &&  day>28) {
         return false;
      }
   }
   return true;

}
/**
 * 工具函数,根据给定的"yyyy-mm-dd,yyyy/mm"等字符串格式自动生成正则表达式
 * @param 日期格式字符串
 * @return 相对应的正则表达式数组
 */
function getRegex(formatter){
   //全角转半角
   formatter = DBC2SBC(formatter);
   if(formatter.isEmpty()) return null;
   //将格式用","分割
   var res = formatter.split(",");
   //返回的正则表达式数组
   var regexs =[];

   if( res != null){
      for(i =0; i < res.length; i++){
         var count = 0;
         var regex = '^';//打印正则表达式开头
         var str = res[i]; //格式字符串
         var splitChar;
         //分割出分割符
         if(str.indexOf("-")> -1) splitChar='-';
         else if(str.indexOf("/")> -1) splitChar='/';
         else if(str.indexOf(".")> -1) splitChar='.';
         //根据分割符构造正则表格式
         var temp = str.split(splitChar);
         if(splitChar == '/') splitChar ='\\\/';
         if(temp.length > 0)
            regex+="\\d{"+temp[0].length+"}";
         if(temp.length > 1){
            if(temp[1].length >1)
               regex+=splitChar+"\\d{1,2}";
         }
         if(temp.length > 2)
            if(temp[2].length >1)
               regex+=splitChar+"\\d{1,2}";
         regex += "$";
         regexs.push(regex);
      }
   }
   return regexs;
}
/**
 *正规化日期字符串，将用户输入的字符串用指定的格式进行格式化。
 *@dateStr时间字符串
 *@transFormatter 用户要求输出的格式
 *@dateFormatter输入时兼容的日期格式
 *如果dateStr匹配兼容的日期格式的任何一种,则认为输入合法,然后将日期字符串转化成用户要求的格式
 *@return 如果匹配成功,则用用户指定的格式输出,否则输出null
 */

function normalizateDateStr(dateStr,transFormatter,dateFormatter){
   //全角转半角
   dateStr = DBC2SBC(dateStr);
   transFormatter = DBC2SBC(transFormatter);
   dateFormatter = DBC2SBC(dateFormatter);
   //判断参数有效性
   if(dateStr.isEmpty() || dateFormatter.isEmpty() || transFormatter.isEmpty())
      return null;
   dateStr = dateStr.trim();//过滤前后空格
   //判断输入,输出格式是否兼容
   //alert("----"+isLegalInputAndOutput(dateStr,transFormatter));
   if(isLegalInputAndOutput(dateStr,transFormatter) == false) return null;

   //根据兼容的字符串生成正则表达式
   var res = getRegex(dateFormatter);
   var r = null;
   //用以上的格式匹配输入的字符串
   if(res == null) return null;
   for(i = 0; i < res.length; i++){
      r = dateStr.match(new RegExp(res[i]));
      if(r != null) break;
   }

   if(r == null) return null; //输入字符串不兼容

   else{ //输入字符串兼容,按照输出格式返回字符串
      dateStr = getStandardDate(dateStr);
      if(dateStr == null) return null;
      var year  = dateStr.substring(0,4);
      var month="01";
      if(dateStr.length > 4)
         var month = dateStr.substring(4,6);
      var day = "01";
      if(dateStr.length > 6) day = dateStr.substring(6,8);
      //判断输入的日期是否是一个合法的日期,否则直接返回
      if(isLegalDate(year,month,day) == false){
         alert("没有"+dataStr+"这个日期，请检查输入！");
         return null;
      }
      //输入格式合乎规格,而且日期合法,按照要求输出的格式进行输出
      //判断输出格式是否有分割符
      var outputSplitChar;
      if(transFormatter.indexOf("-") > -1) outputSplitChar = '-';
      else if(transFormatter.indexOf(".") > -1) outputSplitChar = '.';
      else if(transFormatter.indexOf("/") > -1) outputSplitChar = '/'
      //输出的格式有分割符

      if(outputSplitChar != null && outputSplitChar != ""){
         var outputSplit = transFormatter.split(outputSplitChar);
         if(outputSplit.length == 3) //输出年月日
            return year+outputSplitChar+month+outputSplitChar+day;
         //只输出年月
         if(outputSplit.length ==2 )
            return year+outputSplitChar+month;
         //输出的格式无分割符
      }else{
         if(transFormatter.length == 8)
            return year+month+day;
         else if(transFormatter.length == 6)
            return year+month;
         else return null;
      }

   }
}

/**
 * 获取指定日期中的年，日期字符串和格式字符串中均没有分隔符
 */
function getYearString(dateStr, format){
   //全角转半角
   dateStr = DBC2SBC(dateStr);
   format = DBC2SBC(format);
   var year = "";
   for(var i=0; i<format.length; i++){
      var ch = format.substring(i, i + 1);
      if (ch == "y" || ch == "Y"){ // 是年
         year += dateStr.substring(i, i + 1);
      }
   }
   if (year.length == 2){ // 年只有两位，则补充足四位
      year = "20" + year;
   }
   return year;
}
/**
 * 获取指定日期中的月，日期字符串和格式字符串中均没有分隔符
 */
function getMonthString(dateStr, format){
   //全角转半角
   dateStr = DBC2SBC(dateStr);
   format = DBC2SBC(format);
   var Month = "";
   // 跳过年
   for(var i=0; i<format.length; i++){
      var ch = format.substring(i, i + 1);
      if (ch == "m" || ch == "M"){ // 是月，则使用
         Month += dateStr.substring(i, i + 1);
      }
   }
   if (Month.length == 1){ // 月只有一位，则补充足两位
      Month = "0" + Month;
   }
   return Month;
}
/**
 * 获取指定日期中的日，日期字符串和格式字符串中均没有分隔符
 */
function getDayString(dateStr, format){
   //全角转半角
   dateStr = DBC2SBC(dateStr);
   format = DBC2SBC(format);
   var Day = "";
   // 跳过年
   for(var i=0; i<format.length; i++){
      var ch = format.substring(i, i + 1);
      if (ch == "d" && ch == "D"){ // 是日
         Day += dateStr.substring(i, i + 1);
      }
   }
   if (Day.length == 1){ // 月只有一位，则补充足两位
      Day = "0" + Day;
   }
   return Day;
}
/**
 * 是否是日期的检查。扩展函数，增强函数。可以针对格式参数判断输出的值是否满足。
 * 格式：年必须输入四位数且必须在1900年以后；月日时分秒要么输入两个数字，要么输入一个数字；
 * @param dateStr 被检查的字符串
 * @param format 格式说明，yyyy表示年，mm表示月，dd表示日。可以两位年，一位月（显示不一定是两位），一位日（显示不一定是两位）
 * @param mayShort 允许缩短，即可以比格式中要求的短，但开头必须是完整的，也就是说不能没有开头。
 * @return true/false 是否符合格式要求
 */
function isDateExtend(dateStr, format, mayShort) {
   //全角转半角
   dateStr = DBC2SBC(dateStr);
   format = DBC2SBC(format);
   mayShort = DBC2SBC(mayShort);
   format = format.trim();
   if (format == null || format.length == 0) return false;
   var re; // 正则表达式
   if (format == "yyyy-mm-dd") {
      re = /^\d{4}-\d{1,2}-\d{1,2}$/;
   }else if (format == "yyyymmdd"){
      re = /^\d{8}$/;
   }else if (format == "yymmdd"){
      re = /^\d{6}$/;
   }else if (format == "yyyymm"){
      re = /^\d{6}$/;
   }else if (format == "yymm"){
      re = /^\d{4}$/;
   }else{ // 不支持的格式
      alert("不支持指定格式（" + format + "）！");
      return false;
   }
   var r = dateStr.match(re);
   if (r == null) {
      return false;
   }else {
      format = format.removeChar('-').removeChar('.');
      dateStr = dateStr.removeChar('-').removeChar('.');
      var year = getYearString(dateStr, format);
      var month = getMonthString(dateStr, format);
      var day = getDayString(dateStr, format);
      return isLegalDate(year, month, day);
   }
}
/**
 * 判断指定字符串是符合指定格式要求，可以指定多种格式，如：“yyyymmdd,yyyymm”
 * @param dateStr 输入日期字符串
 * @param formatter 格式字符串，如：“yyyymmdd,yyyymm”
 * @fieldName字段名
 */
function isDateWithMultiFormat(dateStr, formatter, des){
   //全角转半角
   dateStr = DBC2SBC(dateStr);
   formatter = DBC2SBC(formatter);
   des = DBC2SBC(des);
   if (dateStr.isEmpty()) return true; // 如果为空，则认为符合要求
   //判断参数有效性
   if(formatter.isEmpty()) { // 无格式说明，则使用缺省格式
      formatter = "yyyymmdd";
   }
   dateStr = dateStr.trim();//过滤前后空格
   //根据兼容的字符串生成正则表达式
   var res = getRegex(formatter);

   //用以上的格式匹配输入的字符串
   if(res == null) {
      alert("\""+des+"\"不支持要求的日期格式（" + formatter + "）！");
      return false;
   }
   var r = null;
   for(i = 0; i < res.length; i++){
      r = dateStr.match(new RegExp(res[i]));
      if(r != null) break;
   }
   if(r == null) {
      var newFormatter = "";
      if(formatter.length>0){
         var str0 = formatter.split(",")[0];
         var str1 = formatter.split(",")[1];
         var str2 = formatter.split(",")[2];

         if(str0 != null){
            if(str0.length == 4) newFormatter += "2008";
            else if(str0.length == 6) newFormatter += "200807";
            else if(str0.length == 8) newFormatter += "20080710";
         }
         if(str1 != null){
            if(str1.length == 4) newFormatter += ",2008";
            else if(str1.length == 6) newFormatter += ",200807";
            else if(str1.length == 8) newFormatter += ",20080710";
         }
         if(str2 != null){
            if(str2.length == 4) newFormatter += ",2008";
            else if(str2.length == 6) newFormatter += ",200807";
            else if(str2.length == 8) newFormatter += ",20080710";
         }
      }
      alert("\"" + des + "\"输入格式应为指定的日期格式！\r\n例如:" + newFormatter);
      return false; //输入字符串不兼容

   }
   //判断日期是否合法
   var oldDateStr = dateStr; // 记录，用于报错
   dateStr = getStandardDate(dateStr);
   if(dateStr == null) return false;
   var year  = dateStr.substring(0,4);
   var month="01";
   if(dateStr.length > 4)
      month = dateStr.substring(4,6);
   var day = "01";
   if(dateStr.length > 6) day = dateStr.substring(6,8);
   //判断输入的日期是否是一个合法的日期,否则直接返回
   if(isLegalDate(year,month,day) == false){
      alert("\"" + des + "\"中输入的内容（" + oldDateStr + "）不是合法的日期数据!");
      return false;
   }
   return true;
}
/**
 * 判断指定字符串中字符是否由指定字符集合中的字符
 */
function MatchCharacters(str, chars) {
   if(str.length==0){ // 如果为空，则返回true
      return true
   }
   // 判断字符串中的每个字符
   for (i = 0; i < str.length; i++) {
      var checkChar = str.charAt(i);
      if (chars.indexOf(checkChar) == -1) {
         return false;
      }
   }
   return true;
}

/**
 * 判断是否为身份证号码
 * 判断是否为15位数字或18位数字
 */
function isIDCard(str){
   pattern = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
   if (str.length == 0) {
      return true;
   }
   // 判断字符串长度是否符合要求
   if (str.length != 15 && str.length != 18){
      return false;
   }
   // 判断字符串是否由指定字符集合中的字符组成
   return MatchCharacters(str, pattern);
}
/**
 * 返回指定格式的当天日期字符串
 */
function getTodayString(){
   var today = new Date();
   var year = today.getFullYear();
   var month = today.getMonth()+1;
   var day = today.getDate();
   if(month < 10)
      month="0"+month;
   if(day < 10 )
      day ="0"+day;
   var toDayString =year+""+month+""+day;
   return toDayString;
}
/**
 *   对象oobj是一个时间输入文本框，应该包含下面的
 *	属性formatter 输入格式，支持多种格式输入，比如yyyyMMdd,yyyyMM.yyyy等
 *   属性 dtype 必须是 date
 *   属性 special 表明 和基准时间的关系，支持before,beforeAndEqual,after,afterAndEqual
 *   属性 baseTime 可以在xml中配置，如果是取服务器系统时间，模板文件中应配置<baseTime>server</baseTime>
 *   也可以直接指定时间字符串 ，支持yyyymmdd,yyyymm,yyyy格式
 *   属性 des表示该字段描述性语言，错误定位的时候需要打印描述信息
 *	根据special ,baseTime校验函数有效性
 */
function isRegularDate(oobj){
   if(oobj == null) return true;
   var formatter = oobj.format;
   var des = oobj.des;
   var dtype = oobj.dtype;
   var special = oobj.special;
   var baseTime = oobj.baseTime;
   if(formatter == null) formatter =  "yyyymmdd";
   //校验有效性
   if(dtype == null || dtype != "date" ){
      alert("请确认你的dtype字段是日期类型!");
      try{
         oobj.focus();
      }catch(e){}
      return false;
   }
   res = isDateWithMultiFormat(oobj.value,formatter,oobj.des);
   if(!res){
      try{
         oobj.focus();
         oobj.select();
      }catch(e){}
      return false;
   }
   //去掉事件字符串的分隔符
   dateStr = getStandardDate(oobj.value);
   //服务器段未返回时间的话,从客户端取时间
   if(baseTime == null || baseTime =="") baseTime = getTodayString();
   var msg = baseTime;
   if(baseTime == getTodayString()) var msg = "当前时间";
   if(special != null && dateStr != null){
      if(special == "before") {
         if(dateStr >= baseTime) {
            alert("\""+des+"\"必须小于"+msg+"！");
            try{
               oobj.focus();
               oobj.select();
            }catch(e){}
            return false;
         }

      }else if(special == "after"){
         if(dateStr <= baseTime) {
            alert("\""+des+"\"必须大于"+msg+"！");
            try{
               oobj.focus();
               oobj.select();
            }catch(e){}
            return false;
         }

      }else if(special == "beforeAndEqual"){
         if(dateStr > baseTime) {
            alert("\""+des+"\"不能大于"+msg+"！");
            try{
               oobj.focus();
               oobj.select();
            }catch(e){}
            return false;
         }

      }else if(special == "afterAndEqual"){
         if(dateStr < baseTime) {
            alert("\""+des+"\"不能小于"+msg+"！");
            try{
               oobj.focus();
               oobj.select();
            }catch(e){}
            return false;
         }

      }else{
         alert("specail参数配置有误！");
         try{
            oobj.focus();
            oobj.select();
         }catch(e){}
         return false;
      }
      return true;
   }

}
/**
 比较2个时间字符串大小关系,本函数中,不对字符串进行校验,调用者应保证
 beginTime,endTime是符合规格的日期字符串

 @param beginTime
 @param endTime
 beginTime > endTime return -1;
 beginTime = endTime return 0;
 beingTime < endTime return 1;
 */
function dateMatch(beginTime,endTime){
   //全角转半角
   beginTime = DBC2SBC(beginTime);
   endTime = DBC2SBC(endTime);
   if(beginTime == null || endTime == null)
      return 1;
   else{
      beginTime = getStandardDate(beginTime);
      endTime =  getStandardDate(endTime);
      if(beginTime == null || endTime == null) return 1;
      else if(beginTime < endTime ) return 1;
      else if (beginTime == endTime) return 0;
      else return -1;

   }
}
/*
 用于备注附件上传,任免材料中上传考察报告与下面函数配套使用
 */
var arrFileTypes = new Array('doc','xls','txt','pdf','jpg','gif','png','ppt','docx','xlsx','pptx','mmap');
/**
 传入文件名,判断文件扩展名是否与arrTypes数组中的拓展名匹配,如果匹配返回
 true,否则返回false并做出提示
 @param fileName 文件名
 @param arrTypes	文件拓展名数组(拓展名小写,形式如'doc','xls','txt','pdf','jpg','gif','png','ppt','docx','xlsx','pptx','mmap')
 */
function judgeFileType(fileName,arrTypes){
   var position = fileName.lastIndexOf(".");
   var ext = fileName.substring(position+1,fileName.length);
   ext = ext.toLowerCase();
   var strTypes ="";
   for(var i=0;i<arrTypes.length;i++){
      if(ext==arrTypes[i]){
         return true;
      }
      if(i>0){
         strTypes +=",";
      }
      strTypes += arrTypes[i];
   }
   alert('系统不支持您所要上传的文件类型,请上传'+strTypes+'类型的文件!');
   return false;
}

/*
 用于备注附件上传,任免材料中上传考察报告与下面函数配套使用
 */
var flashFileTypes = new Array('flv');
/**
 传入文件名,判断文件扩展名是否与arrTypes数组中的拓展名匹配,如果匹配返回
 true,否则返回false并做出提示
 @param fileName 文件名
 @param arrTypes	文件拓展名数组(拓展名小写,形式如'doc','xls','txt','pdf','jpg','gif','png','ppt','docx','xlsx','pptx','mmap')
 */
function judgeFlashType(fileName,arrTypes){
   var position = fileName.lastIndexOf(".");
   var ext = fileName.substring(position+1,fileName.length);
   ext = ext.toLowerCase();
   var strTypes ="";
   for(var i=0;i<arrTypes.length;i++){
      if(ext==arrTypes[i]){
         return true;
      }
      if(i>0){
         strTypes +=",";
      }
      strTypes += arrTypes[i];
   }
   alert('系统不支持您所要上传的文件类型,请上传'+strTypes+'类型的文件!');
   return false;
}


/**
 将字符串中的全角字符转换成半角字符
 @param str 需要转换的字符串
 */
function DBC2SBC(str){
   var result = '';
   if(str != null){
      for (i=0 ; i<str.length; i++) {
         //获取当前字符的unicode编码
         code = str.charCodeAt(i);
         //在这个unicode编码范围中的是所有的英文字母以及各种字符
         if (code >= 65281 && code <= 65373) {
            //把全角字符的unicode编码转换为对应半角字符的unicode码
            result += String.fromCharCode(str.charCodeAt(i) - 65248);
         }
         //空格
         else if (code == 12288) {
            result += String.fromCharCode(str.charCodeAt(i) - 12288 + 32);
         }
         else {
            result += str.charAt(i);
         }
      }
   }
   return result;
}

/**
 将字符串中的半角字符转换成全角字符
 @param str 需要转换的字符串
 */
function SBC2DBC(str){
   var result = '';
   if(str != null){
      for (i=0 ; i<str.length; i++) {
         //获取当前字符的unicode编码
         code = str.charCodeAt(i);
         //在这个unicode编码范围中的是所有的英文字母以及各种字符
         if (code >= 33 && code <= 125) {
            //把半角字符的unicode编码转换为对应全角字符的unicode码
            result += String.fromCharCode(str.charCodeAt(i) + 65248);
         }
         //空格
         else if (code == 32) {
            result += String.fromCharCode(str.charCodeAt(i) + 12288 - 32);
         }
         else {
            result += str.charAt(i);
         }
      }
   }
   return result;
}