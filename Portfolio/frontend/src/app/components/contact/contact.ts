import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './contact.html',
  styleUrls: ['./contact.scss'],
})
export class Contact {
  formData = {
    name: '',
    email: '',
    subject: '',
    message: ''
  };

  constructor(private http: HttpClient) {}

  onSubmit() {
    // Submit to backend
    this.http.post('http://localhost:8080/api/contacts', this.formData)
      .subscribe({
        next: (response) => {
          console.log('Message sent successfully!', response);
          alert('Message sent successfully! I\'ll get back to you within 24 hours.');
          this.resetForm();
        },
        error: (error) => {
          console.error('Error sending message:', error);
          alert('Failed to send message. Please try again or email directly.');
        }
      });
  }

  resetForm() {
    this.formData = {
      name: '',
      email: '',
      subject: '',
      message: ''
    };
  }
}
