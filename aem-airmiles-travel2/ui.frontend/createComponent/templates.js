const { readFileSync } = require("fs");
const path = require("path");



const templates = {
  default: {
    js: readFileSync(`${__dirname}/templates/template.js`, "utf-8"),
    test: readFileSync(`${__dirname}/templates/template.test.js`, "utf-8"),
    html: readFileSync(`${__dirname}/templates/template.html`, "utf-8"),
    dialog: readFileSync(`${__dirname}/templates/dialog/template.xml`, "utf-8"),
    contentxml: readFileSync(`${__dirname}/templates/template.xml`, "utf-8"),
    sass: readFileSync(`${__dirname}/templates/template.scss`, "utf-8"),
  },
  react: {
    js: readFileSync(`${__dirname}/templates/reactTemplate.js`, "utf-8"),
    test: readFileSync(`${__dirname}/templates/template.test.js`, "utf-8"),
    html: readFileSync(`${__dirname}/templates/reactTemplate.html`, "utf-8"),
    dialog: readFileSync(`${__dirname}/templates/dialog/template.xml`, "utf-8"),
    contentxml: readFileSync(`${__dirname}/templates/template.xml`, "utf-8"),
    sass: readFileSync(`${__dirname}/templates/template.scss`, "utf-8"),
  },
};



exports.templates = templates;
