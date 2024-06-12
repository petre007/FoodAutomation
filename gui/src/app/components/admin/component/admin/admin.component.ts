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

	startRlModelTraining() {
		this.robotService.startRlModelTraining()
	}
}
