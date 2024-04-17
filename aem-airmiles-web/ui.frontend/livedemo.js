const fs = require("fs");
const staticIndexMarkup = require("./staticIndexMarkup.js");

function livedemo(filename) {
  const destinationPath = "./src/main/webpack/corporate/static/index.html";

  fs.readFile(filename, "utf8", function (err, compMarkup) {
    if (err) throw err;

    console.log("New demo component markup: ", compMarkup);

    fs.readFile(destinationPath, "utf-8", function (err, data) {
      if (err) throw err;

      const newValue = staticIndexMarkup(compMarkup);

      fs.writeFile(destinationPath, newValue, "utf-8", function (err, data) {
        if (err) throw err;
        console.log("Done!");
      });
    });
  });
}

module.exports = livedemo;
