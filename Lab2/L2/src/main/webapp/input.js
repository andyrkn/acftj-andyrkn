var ducks = 0;
var ducksToFly = 0;
var ducksKdrama = 0;
(() => {
    ducks = Math.round(Math.random()*50);
    ducksToFly = Math.round(Math.random()*ducks);
    ducksKdrama = Math.round(Math.random()*(ducks-ducksToFly));
    const captchaValue = document.getElementById('captcha-input');
    const value = `If a flock has ${ducks} ducks then ${ducksToFly} ducks decide they want a better life and ${ducksKdrama} ducks are watching KDramas, how many ducks does the flock have left?`
    captchaValue.innerText = value;
})();

async function fillCaptcha() {
    const formSubmit = document.getElementById('form-submit');
    const captchaValue = document.getElementById('captcha-value').value;

    const res = ducks - ducksToFly > 0 ? ducks-ducksToFly : 0;

    if(captchaValue != res){
        alert("Invalid Captcha!");
    }
    else{
        formSubmit.removeAttribute("disabled");
    }
}