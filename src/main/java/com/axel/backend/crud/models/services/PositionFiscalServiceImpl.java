package com.axel.backend.crud.models.services;

import com.axel.backend.crud.models.dao.IPositionFiscalDao;
import com.axel.backend.crud.models.entity.PositionFiscal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PositionFiscalServiceImpl implements IPositionFiscalService{

    @Autowired
    private IPositionFiscalDao fiscalDao;

    @Override
    @Transactional(readOnly = true)
    public List<PositionFiscal> findAll() {
        return fiscalDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PositionFiscal> findAll(Pageable pageable) {
        return fiscalDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public PositionFiscal findById(Long id) {
        return fiscalDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public PositionFiscal save(PositionFiscal positionFiscal) {
        return fiscalDao.save(positionFiscal);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        fiscalDao.deleteById(id);
    }
}
