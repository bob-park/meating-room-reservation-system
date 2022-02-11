package com.m2rs.userservice.service.company.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.m2rs.core.commons.exception.NotFoundException;
import com.m2rs.core.commons.exception.ServiceRuntimeException;
import com.m2rs.core.model.Id;
import com.m2rs.userservice.configure.user.properties.UserProperties;
import com.m2rs.userservice.model.api.company.CompanyDto;
import com.m2rs.userservice.model.api.company.CreateCompany;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.repository.company.CompanyRepository;
import com.m2rs.userservice.service.company.CompanyService;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

    private final UserProperties userProperties;

    private final CompanyRepository companyRepository;

    @Transactional
    @Override
    public CompanyDto createCompany(CreateCompany createCompany) {

        checkArgument(isNotEmpty(createCompany.getName()), "name must be provided.");

        Company company = Company.builder()
            .name(createCompany.getName())
            .build();

        company = companyRepository.save(company);

        return CompanyDto.builder()
            .id(company.getId())
            .name(company.getName())
            .logoPath(company.getLogoPath())
            .createdDate(company.getCreatedDate())
            .lastModifiedDate(company.getLastModifiedDate())
            .build();
    }

    @Transactional
    @Override
    public CompanyDto changeLogo(Id<Company, Long> id, MultipartFile logoFile) {

        Company company = companyRepository
            .findById(id.value())
            .orElseThrow(() -> new NotFoundException(id.getClass(), id.value()));

        String logoDir = userProperties.getCompany().getLogoDir();

        String fileName = UUID.randomUUID().toString();
        String extension = FilenameUtils.getExtension(logoFile.getOriginalFilename());

        String logoPath = company.getId() + ""
            + File.separatorChar
            + fileName
            + "."
            + extension;

        log.debug("logo_path={}", logoPath);

        try {
            FileUtils.copyInputStreamToFile(logoFile.getInputStream(),
                new File(logoDir + File.separatorChar + logoPath));
        } catch (IOException e) {
            throw new ServiceRuntimeException(e.getMessage());
        }

        company.addLogoPath(logoPath);

        return CompanyDto.builder()
            .id(company.getId())
            .name(company.getName())
            .logoPath(company.getLogoPath())
            .createdDate(company.getCreatedDate())
            .lastModifiedDate(company.getLastModifiedDate())
            .build();

    }

}
