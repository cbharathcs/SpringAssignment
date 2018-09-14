import {Injectable} from '@angular/core';

@Injectable()
export class FileUtil {

  constructor() {}

  isCSVFile(file) {
    return file.name.endsWith(".csv");
  }

  getHeaderArray(csvRecordsArr, tokenDelimeter) {
    let headers = csvRecordsArr[0].split(tokenDelimeter);
    let headerArray = [];
    for (let j = 0; j < headers.length; j++) {
      headerArray.push(headers[j]);
    }
    return headerArray;
  }

  validateHeaders(origHeaders, fileHeaaders) {
    if (origHeaders.length != fileHeaaders.length) {
      return false;
    }

    var fileHeaderMatchFlag = true;
    for (let j = 0; j < origHeaders.length; j++) {
      if (origHeaders[j] != fileHeaaders[j]) {
        fileHeaderMatchFlag = false;
        break;
      }
    }
    return fileHeaderMatchFlag;
  }

  getDataRecordsArrayFromCSVFile(csvRecordsArray, headerLength,
    validateHeaderAndRecordLengthFlag, tokenDelimeter) {
    var dataArr = []
    for (let i = 1; i < csvRecordsArray.length; i++) {
      let data = csvRecordsArray[i].split(tokenDelimeter);

         let col = 
         {firstName:data[0].replace(/^"(.*)"$/, '$1'),surName:data[1].replace(/^"(.*)"$/, '$1'),issueCount:data[2].replace(/^"(.*)"$/, '$1'),dob:data[3].replace(/^"(.*)"$/, '$1')}
          
        ;
      
       dataArr.push(col);
      }
     


   
  
    
    return dataArr;
  }

}