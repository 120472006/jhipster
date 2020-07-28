import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { RoleDetailComponent } from 'app/entities/service1/role/role-detail.component';
import { Role } from 'app/shared/model/service1/role.model';

describe('Component Tests', () => {
  describe('Role Management Detail Component', () => {
    let comp: RoleDetailComponent;
    let fixture: ComponentFixture<RoleDetailComponent>;
    const route = ({ data: of({ role: new Role(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [RoleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RoleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RoleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load role on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.role).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
