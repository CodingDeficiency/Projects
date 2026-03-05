import { Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Projects } from './components/projects/projects';
import { Contact } from './components/contact/contact';
import { ExperienceComponent } from './components/experience/experience.component';

export const routes: Routes = [
  { path: '', component: Home }, // Home page
  { path: 'portfolio', component: Projects }, // Portfolio of all projects
  { path: 'contact', component: Contact }, // Contact page
  { path: 'experience', component: ExperienceComponent }, // Experience page
  { path: '**', redirectTo: '' } // Redirect unknown routes to home
];
