const inquirer = require("inquirer");
const { fileURLToPath } = require("url");
const arg = require("arg");
const { readdirSync, readFileSync } = require("fs");
const path = require("path");
const { exec } = require("child_process");
const templates = require("../templates");

const themePath = path.join(__dirname, "..", "..", "src", "main", "webpack");

let themes = readdirSync(themePath, { encoding: "utf-8" });
themes = themes.filter((dir) => dir !== "datalayer" && dir !== "dependencies");

function parseArgumentsIntoOptions(rawArgs) {
  const args = arg(
    {
      "--jcrtitle": String,
      "--name": String,
      "--theme": String,
    },
    {
      argv: rawArgs.slice(2),
    }
  );

  return {
    jcrTitle: args["--jcrtitle"],
    theme: args["--theme"],
    name: args["--name"],
  };
}

async function promptForMissingOptions(options) {
  const questions = [];

  if (!options.jcrTitle) {
    questions.push({
      type: "input",
      name: "jcrTitle",
      message: "Enter the JCR title",
      default: "New Component",
    });
  }
  if (!options.name) {
    questions.push({
      type: "input",
      name: "name",
      message: "Enter the name of your component",
      default: "newcomponent",
    });
  }

  if (!options.template) {
    questions.push({
      type: "list",
      name: "template",
      message: "select template",
      choices: Object.keys(templates.templates),
      default: "default",
    });
  }

  const answers = await inquirer.prompt(questions);

  return {
    ...options,
    jcrTitle: options.jcrTitle || answers.jcrTitle,
    name: options.name || answers.name,
    theme: options.theme || answers.theme,
    template: options.template || answers.template,
  };
}

async function cli(args) {
  let options = parseArgumentsIntoOptions(args);
  options = await promptForMissingOptions(options);

  exec(
    `gulp generateComponent --theme "${options.theme}" --component "${options.name}" --jcrtitle "${options.jcrTitle}" --template "${options.template}"`,
    (error, stdout, stderr) => {
      if (error) {
        console.error(`exec error: ${error}`);
        return;
      }
      console.log(`stdout: ${stdout}`);
      console.error(`stderr: ${stderr}`);
    }
  );
}

module.exports.cli = cli;
