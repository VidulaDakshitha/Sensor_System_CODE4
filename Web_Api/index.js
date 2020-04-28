const express = require("express");
const app = express();
const core = require("cors");
const bodyParser = require("body-parser");

const nodemailer = require("nodemailer");
app.use(core());
app.use(bodyParser());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(__dirname));
var mysql = require("mysql");
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
    database: "ds_sensor_system",
  });

  con.connect(function (err) {
    if (err) throw err;
    console.log("Connected!");
  });
} catch (e) {
  console.log("errot" + e);
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
} catch (e) {
  console.log("error" + e);
}

app.get("/", function (req, res) {
  res.send("index.html");
});
var length = 0;
setInterval(() => {
  var query1 = "SELECT * FROM `sensors` ";

  try {
    con.query(query1, async function (err, result) {
      if (err) {
        //  console.log("update fail"+err)
      } else {
        //   console.log(result.length)
        length = result.length;
      }
    });
  } catch (e) {
    console.log("error" + e);
  }
}, 10000);
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
app.post("/updateSensorLavel", async function (req, res) {
  try {
    var query =
      "UPDATE `sensors` SET `colevel`=" +
      req.body.lavel +
      ",smokelevel=" +
      req.body.smokeLevel +
      " WHERE id=" +
      req.body.id +
      " LIMIT 1 ";
    con.query(
      query,
      await function (err, result) {
        if (err) {
          res.status(500).send("error");
        } else {
          res.send("Sensor added");
        }
        // console.log("1 record inserted");
      }
    );
  } catch (e) {
    console.log("errot" + e);
  }
});

app.get("/getSensorData", async function (req, res) {
  //console.log("get All data");

  try {
    await con.query("SELECT * FROM `sensors`", function (err, result, fields) {
      if (err) {
        res.status(500).send("error");
      } else {
        res.send(result);
      }
    });
  } catch (e) {
    console.log(e);
    res.status(200).send("error try ", e);
  }
});

app.post("/addSensorData", async function (req, res) {
  try {
    var query =
      "INSERT INTO `sensors`(`id`, `name`, `room`, `floor`, `colevel`, `smokelevel`, `status`) " +
      "VALUES (null,' " +
      req.body.name +
      " ',' " +
      req.body.room +
      " ',' " +
      req.body.floorid +
      " ',0,0,'Active')";
    con.query(
      query,
      await function (err, result) {
        if (err) {
          res.status(500).send("error");
        } else {
          res.send("Sensor added");
        }
        // console.log("1 record inserted");
      }
    );
  } catch (e) {
    console.log("errot" + e);
  }
});

app.get("/login", async function (req, res) {
  //console.log("get All data");

  try {
    await con.query("SELECT `password` FROM `admins`", function (
      err,
      result,
      fields
    ) {
      if (err) {
        res.status(500).send("error");
      } else {
        res.send(result);
      }
    });
  } catch (e) {
    console.log(e);
    res.status(200).send("error try ", e);
  }
});

app.post("/editSensor", async function (req, res) {
  try {
    if (req.body.status == "Active") {
      var query =
        "UPDATE `sensors` SET `name`='" +
        req.body.name +
        "', `floor`='" +
        req.body.floor +
        "', `room`='" +
        req.body.room +
        "', `status`='" +
        req.body.status +
        "' WHERE id=" +
        req.body.id +
        " LIMIT 1 ";
    } else {
      var query =
        "UPDATE `sensors` SET `name`= '" +
        req.body.name +
        "', `floor`='" +
        req.body.floor +
        "', `room`='" +
        req.body.room +
        "', `status`='" +
        req.body.status +
        "',`colevel`=0,`smokelevel`=0  WHERE id=" +
        req.body.id +
        " LIMIT 1 ";
    }
    console.log(query);
    con.query(
      query,
      await function (err, result) {
        if (err) {
          console.log(err);
          res.status(500).send("error");
        } else {
          res.send("Sensor added");
        }
      }
    );
  } catch (e) {
    console.log("errot" + e);
  }
});

app.post("/sendmail", async function (req, res) {
  try {
    await con.query("SELECT `email`,`name` FROM `clients`", async function (
      err,
      result,
      fields
    ) {
      if (err) {
        res.status(500).send("error");
      } else {
        res.send(result);

        if (result.length != 0) {
          for (var i = 0; i < result.length; i++) {
            console.log(result[i]["email"].toString());

            console.log("visited the function");
            let testAccount = await nodemailer.createTestAccount();

            // create reusable transporter object using the default SMTP transport
            let transporter = nodemailer.createTransport({
              host: "smtp.ethereal.email",
              port: 587,
              secure: false, // true for 465, false for other ports
              auth: {
                user: "augustus.runolfsson@ethereal.email", // generated ethereal user
                pass: "tYKK6XGp9xQuwPapnX", // generated ethereal password
              },
            });

            // send mail with defined transport object
            let info = await transporter.sendMail({
              from: '"Code4 Fire sensor system 👻" <codefoursliit@gmail.com>', // sender address
              to: result[i]["email"].toString(), // list of receivers
              subject: "Alert Important", // Subject line
              text:
                "The co2 levels and smoke levels have risen above 5. Hurry!! leave the room.", // plain text body
              html:
                "<b>Hey!!! " +
                result[i]["name"].toString() +
                " Alert smoke levels co2 levels high in " +
                req.body.room +
                ". Run to the exit</b> <p>Code4 fire alarming system. Thank you</p>", // html body
            });

            console.log("Message sent: %s", info.messageId);
            // Message sent: <b658f8ca-6296-ccf4-8306-87d57a0b4321@example.com>

            // Preview only available when sending through an Ethereal account
            console.log("Preview URL: %s", nodemailer.getTestMessageUrl(info));
            // Preview URL: https://ethereal.email/message/WaQKMgKddxQDoou...
          }
        }
      }

      await con.query("SELECT `email` FROM `admins`", async function (
        err1,
        result2,
        fields2
      ) {
        if (err1) {
        } else {
          console.log("this is result 2" + result2["email"]);
          console.log("this is result 2" + fields2);

          Object.keys(result2).forEach(async function (key) {
            var row = result2[key];
            console.log(row.email);

            let testAccount = await nodemailer.createTestAccount();

            // create reusable transporter object using the default SMTP transport
            let transporter = nodemailer.createTransport({
              host: "smtp.ethereal.email",
              port: 587,
              secure: false, // true for 465, false for other ports
              auth: {
                user: "augustus.runolfsson@ethereal.email", // generated ethereal user
                pass: "tYKK6XGp9xQuwPapnX", // generated ethereal password
              },
            });

            // send mail with defined transport object
            let info = await transporter.sendMail({
              from: '"Code4 Fire sensor system 👻" <codefoursliit@gmail.com>', // sender address
              to: row.email, // list of receivers
              subject: "Admin Alert Important", // Subject line
              text:
                "The co2 levels and smoke levels have risen above 5. Hurry!! Take immediate action.", // plain text body
              html:
                "<b>Hey Admin!!! Alert smoke levels co2 levels high in " +
                req.body.room +
                ". Take immediate action</b>", // html body
            });

            console.log("Message sent: %s", info.messageId);
            // Message sent: <b658f8ca-6296-ccf4-8306-87d57a0b4321@example.com>

            // Preview only available when sending through an Ethereal account
            console.log("Preview URL: %s", nodemailer.getTestMessageUrl(info));
            // Preview URL: https://ethereal.email/message/WaQKMgKddxQDoou...
          });
        }
      });
    });
  } catch (e) {
    console.log(e);
    res.status(200).send("error try ", e);
  }
});

app.listen(3000, console.log("server Start"));
