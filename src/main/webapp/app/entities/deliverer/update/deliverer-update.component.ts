import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDeliverer, Deliverer } from '../deliverer.model';
import { DelivererService } from '../service/deliverer.service';
import { ICooperative } from 'app/entities/cooperative/cooperative.model';
import { CooperativeService } from 'app/entities/cooperative/service/cooperative.service';

@Component({
  selector: 'jhi-deliverer-update',
  templateUrl: './deliverer-update.component.html',
})
export class DelivererUpdateComponent implements OnInit {
  isSaving = false;

  cooperativesSharedCollection: ICooperative[] = [];

  editForm = this.fb.group({
    id: [],
    idDelivrer: [null, [Validators.required]],
    idCooperative: [],
    name: [],
    surname: [],
    telephone: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
    latitude: [null, [Validators.required]],
    longitude: [null, [Validators.required]],
    cooperative: [],
  });

  constructor(
    protected delivererService: DelivererService,
    protected cooperativeService: CooperativeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deliverer }) => {
      this.updateForm(deliverer);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deliverer = this.createFromForm();
    if (deliverer.id !== undefined) {
      this.subscribeToSaveResponse(this.delivererService.update(deliverer));
    } else {
      this.subscribeToSaveResponse(this.delivererService.create(deliverer));
    }
  }

  trackCooperativeById(_index: number, item: ICooperative): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeliverer>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(deliverer: IDeliverer): void {
    this.editForm.patchValue({
      id: deliverer.id,
      idDelivrer: deliverer.idDelivrer,
      idCooperative: deliverer.idCooperative,
      name: deliverer.name,
      surname: deliverer.surname,
      telephone: deliverer.telephone,
      latitude: deliverer.latitude,
      longitude: deliverer.longitude,
      cooperative: deliverer.cooperative,
    });

    this.cooperativesSharedCollection = this.cooperativeService.addCooperativeToCollectionIfMissing(
      this.cooperativesSharedCollection,
      deliverer.cooperative
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cooperativeService
      .query()
      .pipe(map((res: HttpResponse<ICooperative[]>) => res.body ?? []))
      .pipe(
        map((cooperatives: ICooperative[]) =>
          this.cooperativeService.addCooperativeToCollectionIfMissing(cooperatives, this.editForm.get('cooperative')!.value)
        )
      )
      .subscribe((cooperatives: ICooperative[]) => (this.cooperativesSharedCollection = cooperatives));
  }

  protected createFromForm(): IDeliverer {
    return {
      ...new Deliverer(),
      id: this.editForm.get(['id'])!.value,
      idDelivrer: this.editForm.get(['idDelivrer'])!.value,
      idCooperative: this.editForm.get(['idCooperative'])!.value,
      name: this.editForm.get(['name'])!.value,
      surname: this.editForm.get(['surname'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      cooperative: this.editForm.get(['cooperative'])!.value,
    };
  }
}
