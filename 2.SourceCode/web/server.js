const express = require("express");
const path = require("path");
const port = process.env.PORT || "8081";

const app = express();

app.use(express.static(__dirname + "/bundle"));

app.get("*", (req, res) => {
  res.sendFile(path.resolve(path.join(__dirname, "/bundle"), "index.html"));
});

app.listen(port);
