import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const header = 'Basic ' + btoa('admin:admin123');
    const authReq = req.clone({
      setHeaders: { Authorization: header }
    });
    return next.handle(authReq);
  }
}
