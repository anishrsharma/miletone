import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from './models/employee.model';



@Injectable({
  providedIn: 'root'
})

export class EmployeeService {


  private apiUrl = "https://sturdy-yodel-j7pvqwr9xprfp7rp-3000.app.github.dev/employees";

  constructor(private http: HttpClient) { }

  addEmployee(data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, data);
  }


  getAllEmployees(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}`);
  }

  getEmployeeById(id: number): Observable<any> {
    // id.toString();
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }


  deleteEmployee(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }


  updateEmployee(employee: Employee): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${employee.id}`, employee);
  }



}
