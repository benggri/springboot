class Util {

    setSession(key='', value='') {
        sessionStorage.setItem(key, value);
    }

    getSession(key='') {
        return sessionStorage.getItem(key);
    }

}

const util = new Util();

Util.prototype.isEmptyObj = function(obj) {
    if ( typeof obj === 'undefined' ) {
        return true;
    }
    if ( typeof obj === 'string' && obj === '' ) {
        return true;
    }
    if ( typeof obj === 'function' ) {
        return false;
    }
    if ( typeof obj === 'boolean' ) {
        return false;
    }
    if ( typeof obj === 'number' ) {
        return false;
    }
    if ( typeof obj === 'bigint' ) {
        return false;
    }
    if ( obj instanceof FormData ) {
        return false;
    }
    if ( obj instanceof Promise ) {
        return false;
    }
    if ( typeof obj === 'object' ) {
        if ( obj === null ) {
            return true;
        }
        if ( Array.isArray(obj) && obj.length < 1 ) {
            return true;
        }
        if ( Object.keys(obj).length > 0 ) {
            return false;
        }
        var objStr = JSON.stringify(obj);
        if ( objStr === '{}' || objStr === '[]' ) {
            return true;
        }
    }
    return false;
}

Util.prototype.nvl = function(obj='', defaultVal='') {
    return (util.isEmptyObj(obj) ? defaultVal : obj);
}