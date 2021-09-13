const csvFilePath='instrument.csv'
const csv=require('csvtojson')
var questions = []
csv()
.fromFile(csvFilePath)
.on('json',(jsonObj)=>{
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
    questions.forEach((question) => {
        var step = {
            id: "",
            title: "",
            subtitle: "",
            type: "",
            choices: []
        }
        switch(question["Field Type"]){
            case "radio":
                step.type = "MutlipleChoice"
                if(question['Choices, Calculations, OR Slider Labels'] != ''){
                    choices = question['Choices, Calculations, OR Slider Labels'].split('|')
                    choices.forEach((choice) => {
                        step.choices.push(choice.split(',')[1])
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
            default:
                break;
        }
        template.survey.push(step);
        template.survey.name = question['Form Name'].split('_').join(" ")
    })
    console.log(template)
})

