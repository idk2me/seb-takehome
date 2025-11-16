import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  {
    path: 'history/:code',
    renderMode: RenderMode.Server
  },
  {
    path: '**',
    renderMode: RenderMode.Prerender
  }
];
