var requestsArray= [],
    express = require('express'),
    bodyParser = require('body-parser'),
    app = express();

app.use(bodyParser.json());

app.listen(4004);
console.log('Listening at 4004');

//request GET call
//INPUT: connId, timeout
//OUTPUT: respond with status:ok after given timeout
app.get('/request', function(request, response) {
    setTimeout(function() {
    	var output = {"status": "ok"};
        response.json((output));
    }, request.query.timeout);

    var newRequest= {},
        connId = request.query.connId,
        timeout =  request.query.timeout, 
        startTime = new Date(), 
        parsedDate = new Date(Date.parse(startTime)), 
        endTime = new Date(parsedDate.getTime() + (1*timeout));

    newRequest.startTime  = startTime ;
    newRequest.endTime = endTime;
    newRequest.connId = connId;
    requestsArray.push(newRequest);
});

//serverStatus GET call
//INPUT: N/A
//OUTPUT: respond with status of all the connections that are active with their connId and time left to close the connection
app.get('/serverStatus', function(request, response) {
    var outputArray = [], 
        newDate = new Date(), 
        newDateTime = (new Date(Date.parse(newDate))).getTime();

    for(var i = 0; i<requestsArray.length; i++){
        var j = i;
        (function(j){
            if(requestsArray[j].endTime > newDate){
                var newOutput = {}, 
                    endDateTime = (new Date(Date.parse(requestsArray[j].endTime))).getTime();
                    
                newOutput[requestsArray[j].connId] = JSON.stringify((endDateTime-newDateTime)/1000);
                outputArray.push(newOutput);
            }
            if(j==requestsArray.length-1){
                response.json((outputArray));
            }
        })(j);
    }
    if(requestsArray.length == 0){
        response.json({"status": "no active connection"});
    }
});

//kill PUT call
//INPUT: connId
//OUTPUT: kills the connection with input connId if connection is active; else repond with a message saying invalid connId
app.put('/kill', function (req, res) {
    killConnId({
        connId: req.body.connId,
        date: new Date(),
    }, function (resultArray) {
        res.json((resultArray));
    });
});

function killConnId(config, callback) {
    var found = false;
    for(var i=0; i<requestsArray.length; i++){
        if(requestsArray[i].connId == config.connId && requestsArray[i].endTime > config.date) {
        	requestsArray.splice(i, 1);
        	found = true;
        	break;
        }
    }
    if(found){
    	var output = {"status": "killed"};
        callback(new Object(output));
    } else {
    	var output = {"status": "invalid connection Id "+config.connId};
        callback(new Object(output));
    }
}
