import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../employee.service';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Employee } from '../models/employee.model';

@Component({
  selector: 'app-list-employee',
  templateUrl: './list-employee.component.html',
  styleUrls: ['./list-employee.component.scss']
})


export class ListEmployeeComponent implements OnInit {

  employeeData: Employee[] = [];
  // empData = { id: '', name: '', salary: '', department: '' };


  constructor(
    private http: HttpClient,
    private employeeService: EmployeeService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    // throw new Error('Method not implemented.');

    this.employeeService.getAllEmployees().subscribe((data) => {
      this.employeeData = data;
    });


    // const id = this.route.snapshot.paramMap.get('id')!;
    // this.employeeService.getEmployeeById(id).subscribe((data) => {
    //   this.empData = data;
    // });

  }


  updateEmployee(id: string): void {
    this.router.navigate(['/edit/${id}']); // navigating to edit-employee component
  }

  deleteEmployee(id: string): void {
    this.employeeService.deleteEmployee(id).subscribe(() => {
      this.employeeData = this.employeeData.filter(emp => emp.id !== id);
    });
  }




}
