package com.axel.backend.crud.models.dao;

import com.axel.backend.crud.models.entity.PositionFiscal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPositionFiscalDao extends JpaRepository<PositionFiscal,Long> {

    public List<PositionFiscal> findByOrderByYearDesc();

}
