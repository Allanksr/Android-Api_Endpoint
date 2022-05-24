function doGet(e) {
var json = convertSheet2JsonText(e);
    return ContentService.createTextOutput(JSON.stringify({promo: json}, null, 4))
    .setMimeType(ContentService.MimeType.JSON)
}

function convertSheet2JsonText(request) {
 var code_promo = request.parameter.code_promo
 var arrayOfSheets = ["spread_sheet_ID"]

  var activeSheet = SpreadsheetApp.openById(arrayOfSheets[0]).getActiveSheet()
  var lastRow = activeSheet.getLastRow()
    var lastColumn = activeSheet.getLastColumn()
    var range = activeSheet.getRange(1, 1, lastRow, lastColumn)
    var data = range.getValues()

    var checkData = []
    var checkUsed = []

    for (var i = 1; i < data.length; i++) {
        var row = data[i]
        var code_promo_field = row[0].toString()
        var used_times = row[1].toString()
        checkData.push(code_promo_field)
        checkUsed.push(used_times) 
    }

  var jsonArray = []
  var json = new Object()
  if (checkData.indexOf(code_promo) > -1) {
       var index = checkData.indexOf(code_promo)+1 // line
        if(data[index][1] < data[index][3]){
            data[index][1] = data[index][1]+1
            range.setValues(data)
            json["promo_found"] = true
            json["promo_type"] = data[index][5]
            json["promo_data"] = data[index][2]        
            jsonArray.push(json)
         }else{
           json["promo_found"] = false
           json["promo_type"] = data[index][5]
           json["promo_data"] = data[index][4]        
           jsonArray.push(json)
         }
   }else{
     json["promo_found"] = false
     jsonArray.push(json)
   }
  return jsonArray
}



















