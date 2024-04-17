export const isNotFilled = (element) => {
    switch (typeof (element)) {
        case 'object':
            return element === null || (Array.isArray(element) && (element.length === 0 || /^\s*$/.test(element[0])));
        case 'string':
            return element === "";
        default:
            return false;
    }
};
