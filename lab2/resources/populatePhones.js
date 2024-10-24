numPhones = function () {
  var listPhones = db.phones.aggregate([
      {
          $group: {
              _id: "$components.prefix",
              numPhones: { $sum: 1 }
          }
      },
      {
          $project: {
              _id: 0, 
              prefix: '$_id',
              numPhones: 1 
          }
      }
    ])
  
  return result;
}
