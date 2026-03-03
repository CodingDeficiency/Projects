import { Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Projects } from './components/projects/projects';
import { ProjectDetails } from './components/project-details/project-details';
import { Contact } from './components/contact/contact';
import { ExperienceComponent } from './components/experience/experience.component';

export const routes: Routes = [
  { path: '', component: Home }, // Home page
  { path: 'portfolio', component: Projects }, // Portfolio of all projects
  { path: 'project/:id', component: ProjectDetails }, // Individual project details
  { path: 'contact', component: Contact }, // Contact page
  { path: 'experience', component: ExperienceComponent }, // Experience page
  { path: '**', redirectTo: '' } // Redirect unknown routes to home
];
