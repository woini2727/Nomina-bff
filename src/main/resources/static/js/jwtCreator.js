var header = {
    "alg": "HS256",
    "typ": "JWT"
};
var now = new Date();
var data = {
    "iss": "candidate-EvZz",
    "iat": now / 1000,
    "exp": (now.getTime() + 2*60000) / 1000,
    "aud": "nomina-escribanos-ws",
    "sub": "technical-challenge",
    "role": [ "EXTERNOS" ]
};

var secret = "EvZz2Pd6mUcgjxpwMLrHb9TaW8GRBk3nyKuQ";




/** IMPLEMENTACION EN JS **/
function base64url(source) {
    // Encode in classical base64
    encodedSource = CryptoJS.enc.Base64.stringify(source);

    // Remove padding equal characters
    encodedSource = encodedSource.replace(/=+$/, '');

    // Replace characters according to base64url specifications
    encodedSource = encodedSource.replace(/\+/g, '-');
    encodedSource = encodedSource.replace(/\//g, '_');

    return encodedSource;
}

var stringifiedHeader = CryptoJS.enc.Utf8.parse(JSON.stringify(header));
var encodedHeader = base64url(stringifiedHeader);

var stringifiedData = CryptoJS.enc.Utf8.parse(JSON.stringify(data));
var encodedData = base64url(stringifiedData);

var signature = encodedHeader + "." + encodedData;
signature = CryptoJS.HmacSHA256(signature, secret);
signature = base64url(signature);

var jwtResult = encodedHeader +  "." + encodedData+ "." +signature;
document.getElementById("jwt").value = jwtResult;
console.log(jwtResult)
console.log(document.getElementById("jwt").value)