import CONSTANTS from "../constants";

export const getEnvironment = (runmodesStr) => {
  let environment = CONSTANTS.ENVIRONMENT_NOENV;
  if (/localhost/.test(runmodesStr)) {environment = CONSTANTS.ENVIRONMENT_QA;}
  else if (/dev/.test(runmodesStr)) {environment = CONSTANTS.ENVIRONMENT_DEV;}
  else if (/qa/.test(runmodesStr)) {environment = CONSTANTS.ENVIRONMENT_QA;}
  else if (/stage/.test(runmodesStr)) {environment = CONSTANTS.ENVIRONMENT_STAGE;}
  else if (/prod/.test(runmodesStr)) {environment = CONSTANTS.ENVIRONMENT_PROD;}
  return environment;
};
