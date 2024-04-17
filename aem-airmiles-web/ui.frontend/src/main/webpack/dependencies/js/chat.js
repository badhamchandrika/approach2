import {getBodyAttr, getMemdeets, isAuthed} from "./utils";
import {constants} from "./constants";

export const openLiveChatWidget = () => {
    // Global method to initialize chat widget
    window.Genesys('subscribe', 'Launcher.ready', () => {
        window.Genesys('command', 'Launcher.show', {});
    });
    
    window.Genesys('subscribe', 'Messenger.ready', () => {
        window.Genesys('command', 'Messenger.open', {}, () => {
            sessionStorage.setItem(constants.sessionGenesysChatStatus, 'open');
        });
    })

    const lang = getBodyAttr('lang')=="en" ? "Eng": "Fre";
    let env = getBodyAttr('env');
    const lowEnvironments = ['dev', 'qa', 'stage', 'local'];
    env = lowEnvironments.includes(env) ? 'TEST' : 'PROD';

    const collectorTypeMap = {
        O: 'Onyx',
        G: 'Gold',
        B: 'Blue',
    };

    let collectorType = isAuthed() ? getMemdeets()['tier'] : "";
    collectorType = collectorTypeMap[collectorType] || '';
    
    console.log("collector number: "+(isAuthed() ? getMemdeets()['cardNumber']:"99999999999"));
    console.log("validated:"+isAuthed());
    console.log("language:"+lang);
    console.log("collectorType:"+collectorType);
    console.log("customerFirstName:"+(isAuthed() ? getMemdeets()['firstName'] : "GUEST"));
    console.log("Environment:"+env);
    window.Genesys("command", "Database.set", {
        messaging: {
            customAttributes: {
                collectorNumber: isAuthed() ? getMemdeets()['cardNumber']:"99999999999",
                validated: isAuthed(),
                language: lang,
                collectorType: collectorType,
                "context.firstName": isAuthed() ? getMemdeets()['firstName'] : "GUEST",
                environment: env
            }
        }
    });
}