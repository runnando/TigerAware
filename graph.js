//const csvFilePath='./csvfile/PX040901/instrument.csv'//this one check
//const csvFilePath='./csvfile/PX110301/instrument.csv'//this one check
//const csvFilePath='./csvfile/PX020203/instrument.csv'//this one check
//const csvFilePath='./csvfile/PX020704/instrument.csv'//this one check
//const csvFilePath='./csvfile/PX061101/instrument.csv'
//const csvFilePath='./csvfile/PX150801/instrument.csv'//this one check(change operation)
//const csvFilePath='./csvfile/PX100301/instrument.csv'//this one check, after i change branching question operation to number
const csvFilePath='./csvfile/PX662102/instrument.csv'
//const csvfile = './csvfile/';
const csv=require('csvtojson')
var fs = require('fs')//for saving json to txt file
var questions = []
csv().fromFile(csvFilePath).on('json',(jsonObj)=>{
  // combine csv header row and csv line to a json object
  // jsonObj.a ==> 1 or 4
  if(jsonObj)
  questions.push(jsonObj)
})
.on('done',(error)=>{
  var template = {
    name: "",
    survey: []
  }

  var array_branch = [];//store object trigerQuestion when Branching Logic not equal to blank
  var array_branch_id = [];//only have every question's id
  var ad_list = [];//store all question's id;
  var ad_list_id = [];//store each branching quesion's id
  var ad_list_triger_id = [];//store those triger question's id

  questions.forEach((question,index) => {
    var q1 = {
      previous:"",
      this:"",
      triger:""
    }
      q1.previous = question['Branching Logic'].substring(question['Branching Logic'].search("\\[")+1,question['Branching Logic'].search("\\]"))
      q1.this = question['Variable / Field Name']
      q1.triger = question['Branching Logic'].substring(question['Branching Logic'].search("\'")+1,question['Branching Logic'].lastIndexOf("\'"))
      array_branch.push(q1);
      array_branch_id.push(question['Variable / Field Name']);
  })

  var array_copy = array_branch.slice();//this array_copy store all triger or blank question
  var array_copy_id = [];


  questions.forEach((question) => {
    var step = {
      id: "",
      title: "",
      subtitle: "",
      type: "",
      choices: [],
      condition: [],
      conditionalDefault: ""
    }




    switch(question["Field Type"]){
      case "radio":
      step.type = "MutlipleChoice"
      if(question['Choices, Calculations, OR Slider Labels'] != ''){
        choices = question['Choices, Calculations, OR Slider Labels'].split('|')

        choices.forEach((choice) => {
          step.choices.push(choice);
        })
      }

      step.id = question['Variable / Field Name']
      step.title = question['Field Label']
      break;

      case "text":
      step.type = "TextField"
      step.id = question['Variable / Field Name']
      step.title = question['Field Label']
      break;

      case "yesno":
      step.type = "yesNo"
      step.id = question['Variable / Field Name'];
      step.title = question['Field Label'];
      choices = ["1,Yes","0,No"];
      choices.forEach((choice) => {
        step.choices.push(choice);
      })
      break;

      case "descriptive":
      step.type = "TextField"
      step.id = question['Variable / Field Name']
      step.title = question['Field Label']
      break;

      case "file":
      step.type = "TextFiel"
      step.id = question['Variable / Field Name']
      step.title = question['Field Label']
      break;

      case "calc":
      step.type = "calculate"
      step.id = question['Variable / Field Name']
      step.title = question['Field Label']
      break;

      case "checkbox":
      step.type = "MultipleChoice"
      step.id = question['Variable / Field Name']
      step.title = question['Field Label']
      if(question['Choices, Calculations, OR Slider Labels'] != ''){
        choices = question['Choices, Calculations, OR Slider Labels'].split('|')
        choices.forEach((choice) => {
          step.choices.push(choice);
        })
      }
      break;
      default:
      break;
    }


    //console.log(question['Variable / Field Name']);
    var array_choices = step.choices.slice();
    var pair = {triger:"",toid:""};
    var key_triger = "Null";
    var key_previous = "Null";
    for(var i = 0; i<array_branch.length;i++){
      var pair2 = {previous:"",this:"",next:"^eos",triger:"",id:0,firstOrNot:''};

      if(question['Variable / Field Name'] == array_branch[i].previous){
        pair2.previous = question['Variable / Field Name'];
        pair2.this = array_branch[i].this;
        pair2.triger = array_branch[i].triger;
        for(var j = 0; j<array_branch.length;j++){
          if(question['Variable / Field Name'] == array_branch[j].this){
            pair2.id = j;
          }
        }
        if(key_triger != pair2.triger && key_previous != pair2.previous){
          key_triger = pair2.triger
          key_previous = pair2.previous
          pair2.firstOrNot = "Y";
        }
        else if(key_triger != pair2.triger && key_previous == pair2.previous){
          key_triger = pair2.triger
          key_previous = pair2.previous
          pair2.firstOrNot = "Y";
        }
        else{
          pair2.firstOrNot = "N";
        }
        ad_list.push(pair2);

      }

    }
    //for(var cho=0;cho<step.choices.length;cho++){
    //  console.log(step.choices[cho].split(',')[0]);
  //  }
    //console.log(question['Variable / Field Name']);
    for (var i=0;i<array_branch.length-1;i++){
      if(question['Variable / Field Name'] == array_branch[i].this && array_branch[i+1] != ""){
        step.conditionalDefault = array_branch[i+1].this
        break;
      }
      else if(i == array_branch.length){
        step.conditionalDefault = "^eos"

      }
      else{
        step.conditionalDefault = "^eos"
      }

    }


template.survey.push(step);
template.name = question['Form Name'].split('_').join(" ")
})
////////////////////////////////////////////////////////////////////////////////
//store all branching question's id
////////////////////////////////////////////////////////////////////////////////
for(var i = 0; i<ad_list.length;i++){
  ad_list_id.push(ad_list[i].this);
}
////////////////////////////////////////////////////////////////////////////////
//store all triger question's id;
////////////////////////////////////////////////////////////////////////////////
for(var i=0;i<ad_list.length;i++){
  if(ad_list[i].firstOrNot == "Y" && ad_list_triger_id.includes(ad_list[i].previous) == false){
    ad_list_triger_id.push(ad_list[i].previous);
  }
}
////////////////////////////////////////////////////////////////////////////////
//array_copy is a copy of array_branch without branching question
////////////////////////////////////////////////////////////////////////////////
var count = 0;
for(var i = 0;i<ad_list.length;i++){

  for(var j =0;j<array_branch.length;j++){
    if(ad_list[i].this == array_branch[j].this){
      array_copy.splice(j-count,1);
      count++;
    }
    else{
      continue;
    }

  }
}
////////////////////////////////////////////////////////////////////////////////
//store all blank or triger question's id
////////////////////////////////////////////////////////////////////////////////
for(var i=0;i<array_copy.length;i++){
  array_copy_id.push(array_copy[i].this);
}
if(ad_list.length>0){
  ////////////////////////////////////////////////////////////////////////////////
  //define ever question's next question
  ////////////////////////////////////////////////////////////////////////////////
    for(var i = 0;i<ad_list.length-1;i++){
      if(ad_list[i].previous == ad_list[i+1].previous && ad_list[i].triger == ad_list[i+1].triger){
        ad_list[i].next = ad_list[i+1].this;

      }
      else if(ad_list[i].previous !== ad_list[i+1].previous){
        if(array_copy_id.includes(ad_list[i].previous) == true){
          var index = array_copy_id.indexOf(ad_list[i].previous);
          if(index+1 == array_copy_id.length){
            ad_list[i].next = "^eos";
          }
          else{
            ad_list[i].next = array_copy_id[index+1];
          }
        }
        else if(ad_list_triger_id.includes(ad_list[i].previous) == true){
          index = i;
          console.log(index);
          console.log(ad_list[index].previous);
          do{
            index--;
          }
          while(array_copy_id.includes(ad_list[index].previous) == false);
          //console.log(ad_list[index].this);
          var key = array_copy_id.indexOf(ad_list[index].previous);
          //console.log(key);
          if(key<array_copy_id.length-1){
            ad_list[i].next = array_copy_id[key+1];
          }
          else if(key>=array_copy_id.length-1){
            ad_list[i].next = "^eos";
          }
        }


      }
      else if(ad_list[i].previous == ad_list[i+1].previous && ad_list[i].triger !== ad_list[i+1].triger){
        var index = array_copy_id.indexOf(ad_list[i].previous);
        //console.log(index);
        if(index+1 == array_copy_id.length){
          ad_list[i].next = "^eos";
        }
        else{
          ad_list[i].next = array_copy_id[index+1];
        }

      }


    }
  ///////////////////////////////////////////////////////////////////////////////
  //handle last question of branching question
  ///////////////////////////////////////////////////////////////////////////////

    if(array_copy_id.includes(ad_list[ad_list.length-1].previous) == true){
      var last = array_copy_id.indexOf(ad_list[ad_list.length-1].previous);
      //console.log(ad_list[ad_list.length-1].this);
      console.log(last)
      if(last+1 == array_copy_id.length){
        console.log("Yes")
      }
      else{
        //console.log(array_copy_id[last+1]);
        ad_list[ad_list.length-1].next = array_copy_id[last+1]
      }
    }
    else if(ad_list_id.includes(ad_list[ad_list.length-1].previous) == true){
      var swt= ad_list.length-1;
      var last;
      do{last = ad_list_id.indexOf(ad_list[swt].previous);swt=last;}while(ad_list_id.includes(ad_list[swt].previous) == true);
      var last2 = array_copy_id.indexOf(ad_list[last].previous);
      //console.log(ad_list[ad_list.length-1].this);
      //console.log(last2)
      if(last+1 == array_copy_id.length){
        console.log("Yes")
      }
      else{
        //console.log(array_copy_id[last+1]);
        ad_list[ad_list.length-1].next = array_copy_id[last2+1];
      }
    }

  ////////////////////////////////////////////////////////////////////////////////
  //final step
  ////////////////////////////////////////////////////////////////////////////////
  template.survey.forEach((survey1)=>{

    if(ad_list_id.includes(survey1.id) == true){
      //console.log(survey1.id + "cond1");
        for(var j = 0; j<ad_list.length; j++){
          if(ad_list[j].this == survey1.id){
            survey1.conditionalDefault = ad_list[j].next;
          }

        }

    }
    else if(array_copy_id.includes(survey1.id) == true && ad_list_triger_id.includes(survey1.id) == true){
      //console.log(survey1.id + "cond2");
      for(var cho = 0; cho<survey1.choices.length;cho++){
        var cond = {triger:"",toid:"^eos"};
        cond.triger = cho;
        for(var i = 0;i<array_copy_id.length-1;i++){
          if(survey1.id == array_copy_id[i]){
            cond.toid = array_copy_id[i+1];
          }
          //else{
            //cond.toid = "^eos"
          //}
        }
        for(var i = 0; i < ad_list.length; i++){
          if(survey1.choices[cho].slice(",")[0] == ad_list[i].triger && survey1.id == ad_list[i].previous && ad_list[i].firstOrNot == "Y"){
            cond.toid = ad_list[i].this;
            survey1.conditionalDefault = "^eos";
          }
          else{
            continue;
            //survey1.conditionalDefault = "sadasd";
          }
        }

        survey1.condition.push(cond);
      }

    }
    else if(array_copy_id.includes(survey1.id) == false && ad_list_triger_id.includes(survey1.id)){
      console.log(survey1.id + "cond3");
      //survey1.conditionalDefault = "XXYYZZ";
      /*for(var cho = 0; cho<survey1.choices.length;cho++){
        var cond = {triger:"",toid:"^eos"};
        cond.triger = cho;
        survey1.conditionalDefault = "^wewew";
        for(var i = 0; i<ad_list.length;i++){
          if(survey1.choices[cho].slice(",")[0] == ad_list[i].triger && survey1.id == ad_list[i].previous && ad_list[i].firstOrNot == "Y"){
            cond.toid = ad_list[i].this;
            survey1.condition.push(cond);
          }
        }
      }*/
    }
    else{
      console.log(survey1.id + "cond4");
      //console.log("pass")
    }





  })
  ////////////////////////////////////////////////////////////////////////////////
  //fix ad_list problem, when branching quesiton is a triger question;
  ////////////////////////////////////////////////////////////////////////////////
  template.survey.forEach((survey1)=>{
    if(ad_list_id.includes(survey1.id)){
      survey1.choices.forEach((cho)=>{
        var cond = {triger:"",toid:"^eos"}
        cond.triger = survey1.choices.indexOf(cho);
        var index111 = ad_list_id.indexOf(survey1.id);
        //console.log(index111);
        if(array_copy_id.includes(ad_list[index111].previous)==true){
          for(var i = index111;i < ad_list.length;i++){
            if(ad_list[i].previous == survey1.id && ad_list[i].firstOrNot == "Y" && cho.slice(",")[0] == ad_list[i].triger){
              //console.log("asdasdasdasdsadsadwewew")
              cond.toid = ad_list[i].this;
              survey1.conditionalDefault = "^eos"
            }
          }
          if(cond.toid == "^eos"){
            if(ad_list[index111].next != "^eos"){
              cond.toid = ad_list[index111].next;
            }
          }
          survey1.condition.push(cond);
        }
        else if(ad_list_id.includes(ad_list[index111].previous)==true){
          index222 = index111;
          do{
            index222--;
          }
          while(array_copy_id.includes(ad_list[index222].previous) == false)
          for(var i = index111; i<ad_list.length; i++){
            if(ad_list[i].previous == survey1.id && ad_list[i].firstOrNot == "Y" && cho.slice(",")[0] == ad_list[i].triger){
              cond.toid = ad_list[i].this;
              survey1.conditionalDefault = "^eos"
            }
          }
          if(cond.toid == "^eos"){
            if(ad_list[index222].next != "^eos"){
              cond.toid = ad_list[index222].next;
            }
          }
          survey1.condition.push(cond);
        }
      })
    }
  })
}



var filename = "PX662102"+"data.json";
fs.writeFile(filename,JSON.stringify(template),function(err){
  if(err){
    return console.log(err);
  }
})
//console.log(array_copy);
//console.log(array_copy_id);
//console.log(ad_list_id);
//console.log(array_branch);
//console.log(template.survey[1]);
//console.log(array_branch_id);
//console.log(ad_list_triger_id);
console.log(ad_list);
})
