const express = require('express');
const app= express();
const core = require('cors');
const bodyParser = require('body-parser');
app.use(core())
app.use(bodyParser())
app.use(express.static(__dirname));
var mysql = require('mysql');
//var nodemailer = require();

/*CREATE table sensors(
   id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(100),
    room varchar(100),
    floor varchar(100),
    colevel REAL
    )*/

try {
    var con = mysql.createConnection({
        host: "localhost",
        user: "root",
        password: "",
        database: "ds_sensor_system"
    });


    con.connect(function(err) {
        if (err) throw err;
        console.log("Connected!");
    });
}catch (e) {
    console.log("errot"+e);
}

try {
 /* var transport =nodemailer.creatTransport({
      service:'gmail',
      auth:{
          user:'',
          pass:''
      }
  });
*/

}catch (e) {
    console.log("error"+e);
}



app.get('/',function (req,res) {
    res.send('index.html');
});
var length=0;
setInterval(()=>{

    var query1="SELECT * FROM `sensors` ";

try {
        con.query(query1, async function (err, result) {
            if (err) {
              //  console.log("update fail"+err)
            } else {
             //   console.log(result.length)
              length=result.length;
            }
        })





    }catch (e) {
    console.log("error"+e);
}

},10000);
/*
setInterval(()=>{



    try {


        for (var i=1;i<=length;i++){
            var lavel=Math.floor(Math.random() * 101);
            var query="UPDATE `sensors` SET `colevel`="+lavel+" WHERE id="+i+" LIMIT 1 ";
            con.query(query, function (err, result) {
                if (err) {
                  //  console.log("update fail"+err)
                } else {
                  //  console.log("updated")
                }
            })
        }



    }catch (e) {
        console.log("error"+e);
    }

},15000);
*/
app.post('/updateSensorLavel',async function (req,res){

    try {
        var query="UPDATE `sensors` SET `colevel`="+req.body.lavel+" WHERE id="+req.body.id+" LIMIT 1 ";
        con.query(query,await function (err, result) {
            if (err) {
                res.status(500).send("error");
            }else{
                res.send("Sensor added");
            }
           // console.log("1 record inserted");
        });
    }catch (e) {
        console.log("errot"+e);

    }


});

app.get('/getSensorData',async function (req,res) {

//console.log("get All data");

    try {
        await  con.query("SELECT * FROM `sensors`", function (err, result, fields) {
            if (err) {
                res.status(500).send("error");
            }else{

                res.send(result);
            }


        });

    }catch (e) {
        console.log(e);
        res.status(200).send("error try ",e);
    }
});

app.post('/addSensorData',async function (req,res){

    try {
        var query ="INSERT INTO `sensors`(`id`, `name`, `room`, `floor`, `colevel`) " +
            "VALUES (null,' "+req.body.name+" ',' "+req.body.room+" ',' "+req.body.floorid+" ',0)";
        con.query(query,await function (err, result) {
            if (err) {
                res.status(500).send("error");
            }else{
                res.send("Sensor added");
            }
           // console.log("1 record inserted");
        });
    }catch (e) {
        console.log("errot"+e);

    }


});


app.post('/editSensor',async function (req,res){

    try {

        var query="UPDATE `sensors` SET `name`='"+req.body.name+"', `floor`='"+req.body.floor+"', `room`='"+req.body.floor+"', `status`='"+req.body.status+"' WHERE id="+req.body.id+" LIMIT 1 ";
       console.log(query);
        con.query(query,await function (err, result) {
            if (err) {
                console.log(err);
                res.status(500).send("error");
            }else{
                res.send("Sensor added");
            }
           // console.log("1 record inserted");
        });
    }catch (e) {
        console.log("errot"+e);

    }


});


app.get('/login',async function (req,res) {

//console.log("get All data");

    try {
        await  con.query("SELECT `password` FROM `admins`", function (err, result, fields) {
            if (err) {
                res.status(500).send("error");
            }else{

                res.send(result);
            }


        });

    }catch (e) {
        console.log(e);
        res.status(200).send("error try ",e);
    }
});



app.listen(3000,console.log("server Start"));