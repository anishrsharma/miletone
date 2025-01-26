import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.scss']
})
export class AddEmployeeComponent implements OnInit {
  employeeForm: any;
  // employeeService: any;

  constructor(public fb: FormBuilder, private employeeService: EmployeeService) {
    this.employeeForm = this.fb.group({
      name: ['', Validators.required],
      salary: ['', Validators.required],
      department: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    // throw new Error('Method not implemented.');
  }

  addEmployee(): void {
    console.log("adding employee...");
    this.employeeService.addEmployee(this.employeeForm.value).subscribe((data: any) => {
      console.log('Employee Added ...');
      console.log(data);
    });

  }



}
