import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

type Category = 'All' | 'Web Development' | 'Mobile Apps' | 'UI/UX' | 'Open Source';

type ProjectAction = {
  label: string;
  icon: string;
  href: string;
};

type ProjectItem = {
  id?: number;
  title: string;
  description: string;
  categories: Category[];
  badge: string;
  imageTheme: 'teal' | 'peach' | 'code' | 'dark' | 'light';
  actions: ProjectAction[];
  technologies?: string;
  imageUrl?: string;
  projectUrl?: string;
  githubUrl?: string;
  projectDate?: string;
};

@Component({
  selector: 'app-projects',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './projects.html',
  styleUrls: ['./projects.scss'],
})
export class Projects {
  categories: Category[] = ['All', 'Web Development', 'Mobile Apps', 'UI/UX', 'Open Source'];
  activeCategory: Category = 'All';

  projects: ProjectItem[] = [
    {
      id: 1,
      title: 'E-Commerce Dashboard',
      description:
        'A comprehensive analytics dashboard for online retailers providing real-time insights and performance tracking.',
      categories: ['Web Development', 'UI/UX'],
      badge: 'REACT',
      imageTheme: 'teal',
      actions: [
        { label: 'DEMO', icon: 'fa-regular fa-eye', href: 'https://example.com' },
        { label: 'CODE', icon: 'fa-solid fa-code', href: 'https://github.com/CodingDeficiency' },
      ],
    },
    {
      id: 2,
      title: 'Task Manager App',
      description:
        'Cross-platform mobile application for personal productivity with cloud sync and offline-first workflows.',
      categories: ['Mobile Apps', 'UI/UX'],
      badge: 'FLUTTER',
      imageTheme: 'peach',
      actions: [
        { label: 'DEMO', icon: 'fa-regular fa-eye', href: 'https://example.com' },
        { label: 'CODE', icon: 'fa-solid fa-code', href: 'https://github.com/CodingDeficiency' },
      ],
    },
    {
      id: 3,
      title: 'Social Media API',
      description:
        'Robust RESTful API handling authentication, post interactions, and analytics with rate-limiting and caching.',
      categories: ['Web Development', 'Open Source'],
      badge: 'NODE.JS',
      imageTheme: 'code',
      actions: [
        { label: 'DOCS', icon: 'fa-regular fa-file-lines', href: 'https://example.com' },
        { label: 'CODE', icon: 'fa-solid fa-code', href: 'https://github.com/CodingDeficiency' },
      ],
    },
    {
      id: 4,
      title: 'Weather Widget',
      description:
        'A lightweight, embeddable weather widget consuming OpenWeatherMap with clean UI and theming options.',
      categories: ['UI/UX', 'Web Development'],
      badge: 'VUE.JS',
      imageTheme: 'dark',
      actions: [
        { label: 'DEMO', icon: 'fa-regular fa-eye', href: 'https://example.com' },
        { label: 'CODE', icon: 'fa-solid fa-code', href: 'https://github.com/CodingDeficiency' },
      ],
    },
    {
      id: 5,
      title: 'Portfolio v1',
      description:
        'The first iteration of my personal portfolio site, built with semantic HTML/CSS and performance in mind.',
      categories: ['Web Development', 'UI/UX'],
      badge: 'HTML/CSS',
      imageTheme: 'light',
      actions: [
        { label: 'VISIT', icon: 'fa-solid fa-arrow-up-right-from-square', href: 'https://example.com' },
        { label: 'CODE', icon: 'fa-solid fa-code', href: 'https://github.com/CodingDeficiency' },
      ],
    },
    {
      id: 6,
      title: 'Realtime Chat',
      description:
        'A responsive chat application featuring private messaging, rooms, typing indicators, and Socket.IO events.',
      categories: ['Web Development', 'Open Source'],
      badge: 'SOCKET.IO',
      imageTheme: 'peach',
      actions: [
        { label: 'DEMO', icon: 'fa-regular fa-eye', href: 'https://example.com' },
        { label: 'CODE', icon: 'fa-solid fa-code', href: 'https://github.com/CodingDeficiency' },
      ],
    },
  ];

  setCategory(category: Category) {
    this.activeCategory = category;
  }

  get filteredProjects(): ProjectItem[] {
    if (this.activeCategory === 'All') return this.projects;
    return this.projects.filter((p) => p.categories.includes(this.activeCategory));
  }
}
