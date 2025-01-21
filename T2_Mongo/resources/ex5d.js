capicua = function () {
    var listPhones = db.phones.find({});

    listPhones.forEach(element => {
        const num = element.display.split("-")[1];
        const numReverse = num.split("").reverse().join("");
        if (num == numReverse) {
            print(num + " is a capicua")
        }
    });
}
  