const http = require('http');
const fs = require('fs');

http.createServer((req,res)=>{
		res.writeHead(200,{"Content-Type":'text/plain','charset':'utf-8','Access-Control-Allow-Origin':'*','Access-Control-Allow-Methods':'PUT,POST,GET,DELETE,OPTIONS'});

			str = fs.readFileSync('data.json');
			res.write(str);
			res.end();
		
}).listen('9999',()=>{
	console.log('listen in 9999');
});

