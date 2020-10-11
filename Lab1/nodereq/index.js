const axios = require("axios").default;


async function run(){
    var result = await axios.get("http://localhost:8080/words?letters=asdf");
    result.data.forEach(element => {
        console.log(element);
    });
}

run();