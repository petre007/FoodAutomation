import { Component, OnInit } from '@angular/core';
import { RobotService } from '../../service/robot.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor(private robotService: RobotService) {}

  ngOnInit(): void {
    console.log(this.getOutputData("AUTONOMOUS"))
  }

  displayUltrasonicData = () => {
    var dps: { y: number; }[] = [];
    this.robotService.getUltrasonicData(1).subscribe(data=>{
      for (let i=0; i<data.length; i++) {
        let y = data[i]
        dps.push({y : data[i]});
      }
      })
      return dps;
  }

  getOutputData = (type: string) => {
    let output_data: number[] | undefined = []
    var dps: {x:number,  y: number; }[] = [];
    this.robotService.getOutputData(1).subscribe(data=>{
      switch (type) {
        case "MANUAL" : output_data = data['MANUAL']; break
        case "AUTONOMOUS" : output_data = data['AUTONOMOUS']; break
        default: output_data = []; break
      }
      for (let i=0; i<output_data.length; i++) {
        let y = i
        dps.push( {x:y , y:output_data[i]});
      }
      console.log(dps)
    })
    return dps;
  }

  title = 'angular17ssrapp';
	chartOptions = {
	  zoomEnabled: true,
	  exportEnabled: true,
	  theme: "light2",
	  title: {
		text: "Ultrasonic Data"
	  },
	  data: [{
		type: "line",
		dataPoints: this.displayUltrasonicData()
	  }]
	}

  chartOptionsCommands = {
	  animationEnabled: true,
	  theme: "light2",
	  title:{
		text: "Robot commands"
	  },
	  toolTip: {
		shared: true
	  },
	  legend: {
		cursor: "pointer",
		itemclick: function (e: any) {
			if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
				e.dataSeries.visible = false;
			} else {
				e.dataSeries.visible = true;
			} 
			e.chart.render();
		}
	  },
	  data: [{
		type: "line",
		showInLegend: true,
		name: "Manual Commands",
		dataPoints: this.getOutputData("MANUAL")
	  }, {
		type: "line",
		showInLegend: true,
		name: "RL model commands",
		dataPoints: this.getOutputData("AUTONOMOUS")
	  }]
	}	

	startRlModelTraining() {
		this.robotService.startRlModelTraining()
	}
}
