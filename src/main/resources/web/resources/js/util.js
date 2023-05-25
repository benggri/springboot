class Util {

    setSession(key='', value='') {
        sessionStorage.setItem(key, value);
    }

    getSession(key='') {
        return sessionStorage.getItem(key);
    }

    dateFormat(dateStr='20010123123456', format='yyyy-MM-dd HH:mm:ss') {
        if (dateStr.length < 14) {
            dateStr += '0'.repeat(14-dateStr.length);
        }

        let result = '';
        switch (format) {
            case 'yyyy-MM-dd HH:mm:ss' :
                result = dateStr.substring(0, 14).replace(/(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})/g, '$1-$2-$3 $4:$5:$6');
                break;
            case 'yyyy-MM-dd HH:mm' :
                result = dateStr.substring(0, 12).replace(/(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})/g, '$1-$2-$3 $4:$5');
                break;
            case 'yyyy-MM-dd HH' :
                result = dateStr.substring(0, 10).replace(/(\d{4})(\d{2})(\d{2})(\d{2})/g, '$1-$2-$3 $4');
                break;
            case 'yyyy-MM-dd' :
                result = dateStr.substring(0, 8).replace(/(\d{4})(\d{2})(\d{2})/g, '$1-$2-$3');
                break;
            case 'yyyy-MM' :
                result = dateStr.substring(0, 6).replace(/(\d{4})(\d{2})/g, '$1-$2');
                break;
            case 'yyyy' :
                result = dateStr.substring(0, 4).replace(/(\d{4})/g, '$1');
                break;
            default :
        }
        return result;
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