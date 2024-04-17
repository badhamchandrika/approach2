const fs = require('fs-extra');

/*
*
*   Usage:
*   writeFiles(fileName, fileData);
*
*/
const writeFiles = (fileName, fileData) => {
    return fs.outputFile(fileName, fileData);
}

exports.writeFiles = writeFiles;