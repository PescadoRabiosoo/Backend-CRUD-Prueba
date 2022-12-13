package com.axel.backend.crud.models.services;

import com.axel.backend.crud.models.entity.PositionFiscal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPositionFiscalService {

    public List<PositionFiscal> findAll();

    public Page<PositionFiscal> findAll(Pageable pageable);

    public PositionFiscal findById(Long id);

    public PositionFiscal save(PositionFiscal positionFiscal);

    public void delete(Long id);

}
