const fs = require('fs-extra');

/*
*
*   Usage: 
*   readFiles(directory, fileType);
*
*/
const readFiles = (directory, fileType) => {
    const fileLocation = `${directory}.${fileType}`;
    let fileData = fs.readFileSync(fileLocation, 'utf8');

    try {
        fileData = JSON.parse(fileData)
    } catch {
        fileData = fileData
    }

    return fileData;
}

exports.readFiles = readFiles;