import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-experience',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './experience.component.html',
  styleUrls: ['./experience.component.scss'],
})
export class ExperienceComponent {
  profile = {
    name: 'Adams Daouda',
    title: 'Software Engineer',
    location: 'Houston, TX (Open to Remote)',
    email: 'mail@adamsdaouda.com',
    linkedin: 'https://www.linkedin.com',
    github: 'https://github.com/CodingDeficiency',
  };

  summary =
    "Passionate software engineer focused on building scalable, user-centered systems. I enjoy working across the stack, optimizing performance, and delivering polished experiences end-to-end.";

  skills = {
    languages: ['Java','TypeScript', 'JavaScript','SQL'],
    frameworks: ['Angular', 'Spring Boot', 'Node.js', 'Express'],
    tools: ['Docker', 'AWS', 'Git', 'Postman'],
    concepts: ['System Design', 'REST APIs', 'Microservices', 'CI/CD'],
  };

  education = [
    {
      degree: 'B.S. Computer Science',
      school: 'University',
      years: '2019 - 2023',
    },
  ];

  certifications = [
    {
      name: 'AWS Certified Solutions Architect',
      issuer: 'Amazon Web Services',
      year: '2024',
    },
  ];

  experiences = [
    {
      role: 'Software Engineer',
      company: 'Company Name',
      years: '2023 - Present',
      bullets: [
        'Built and maintained features for web applications with Angular and TypeScript.',
        'Designed and integrated REST APIs and improved performance of critical user flows.',
        'Collaborated with cross-functional teams to deliver releases on schedule.',
      ],
    },
    {
      role: 'Junior Software Engineer',
      company: 'Company Name',
      years: '2022 - 2023',
      bullets: [
        'Delivered UI and API enhancements and helped reduce bugs through improved testing and code reviews.',
        'Worked closely with product/design to implement responsive components and consistent UX patterns.',
      ],
    },
    {
      role: 'Software Engineer Intern',
      company: 'Company Name',
      years: '2021 - 2022',
      bullets: [
        'Implemented UI components and improved accessibility and responsiveness.',
        'Assisted in backend feature development and testing across environments.',
      ],
    },
  ];

  projects = [
    {
      name: 'CloudScale Monitor',
      description: 'An open-source resource monitoring dashboard with charts and alerts.',
      href: 'https://github.com/CodingDeficiency',
      tags: ['Angular', 'Node.js', 'AWS'],
    },
    {
      name: 'Ecommerce API Kit',
      description: 'A modular backend starter with auth, payments, and product management.',
      href: 'https://github.com/CodingDeficiency',
      tags: ['Java', 'Spring', 'PostgreSQL'],
    },
  ];
}
