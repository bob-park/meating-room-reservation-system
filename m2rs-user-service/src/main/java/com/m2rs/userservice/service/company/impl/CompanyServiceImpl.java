package com.m2rs.userservice.service.company.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.m2rs.core.commons.exception.NotFoundException;
import com.m2rs.core.commons.exception.ServiceRuntimeException;
import com.m2rs.core.commons.model.api.response.Pagination;
import com.m2rs.core.commons.model.service.page.ServicePage;
import com.m2rs.core.model.Id;
import com.m2rs.userservice.configure.user.properties.UserProperties;
import com.m2rs.userservice.model.api.company.CompanyResponse;
import com.m2rs.userservice.model.api.company.CreateCompanyRequest;
import com.m2rs.userservice.model.api.company.SearchCompanyRequest;
import com.m2rs.userservice.model.api.company.UpdateCompanyRequest;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.repository.company.CompanyRepository;
import com.m2rs.userservice.repository.company.query.SearchCompanyCondition;
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

    @Override
    public ServicePage<CompanyResponse> search(SearchCompanyRequest searchCompanyRequest,
        Pageable pageable) {

        SearchCompanyCondition condition = SearchCompanyCondition.builder()
            .name(searchCompanyRequest.getName())
            .build();

        Page<Company> pageResult = companyRepository.search(condition, pageable);

        List<CompanyResponse> contents =
            pageResult.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return ServicePage.<CompanyResponse>builder()
            .contents(contents)
            .page(Pagination.builder()
                .totalCount(pageResult.getTotalElements())
                .page(pageResult.getNumber())
                .lastPage(pageResult.getTotalPages())
                .build())
            .build();
    }

    @Transactional
    @Override
    public CompanyResponse createCompany(CreateCompanyRequest createCompany) {

        checkArgument(isNotEmpty(createCompany.getName()), "name must be provided.");

        Company company = Company.builder()
            .name(createCompany.getName())
            .build();

        company = companyRepository.save(company);

        return toResponse(company);
    }

    @Transactional
    @Override
    public CompanyResponse changeLogo(Id<Company, Long> id, MultipartFile logoFile) {

        Company company = companyRepository
            .findById(id.value())
            .orElseThrow(() -> new NotFoundException(id.getClass(), id.value()));

        if (isNotEmpty(company.getLogoPath())) {
            try {
                removePrevLogo(company.getLogoPath());
            } catch (IOException e) {
                throw new ServiceRuntimeException(e.getMessage());
            }

        }

        String fileName = UUID.randomUUID().toString();
        String extension = FilenameUtils.getExtension(logoFile.getOriginalFilename());

        String logoPath = company.getId() + ""
            + File.separatorChar
            + fileName
            + "."
            + extension;

        try {
            writeLogo(logoFile.getInputStream(), logoPath);
        } catch (IOException e) {
            throw new ServiceRuntimeException(e.getMessage());
        }

        company.addLogoPath(logoPath);

        return toResponse(company);

    }

    @Transactional
    @Override
    public CompanyResponse updateCompany(Id<Company, Long> id,
        UpdateCompanyRequest updateCompanyRequest) {

        Company company =
            companyRepository.findById(id.value())
                .orElseThrow(() -> new NotFoundException(Company.class, id.value()));

        company.changeName(updateCompanyRequest.getName());

        return toResponse(company);
    }

    private void removePrevLogo(String logoPath) throws IOException {

        File logoFile = new File(getAbsolutePath(logoPath));

        if (!logoFile.exists()) {
            log.warn("No exist prev logo file.");
            return;
        }

        FileUtils.forceDelete(logoFile);

        log.debug("success deleted prev logo file.");
    }

    private void writeLogo(InputStream input, String logoPath) throws IOException {
        FileUtils.copyInputStreamToFile(input,
            new File(getAbsolutePath(logoPath)));

        log.debug("success saved logo file.");

    }

    private String getAbsolutePath(String logoPath) {

        String absolutePath = userProperties.getCompany().getLogoDir()
            + File.separatorChar
            + logoPath;

        log.debug("absolute logo path : {}", absolutePath);

        return absolutePath;
    }

    private CompanyResponse toResponse(Company company) {

        return CompanyResponse.builder()
            .id(company.getId())
            .name(company.getName())
            .logoPath(company.getLogoPath())
            .createdDate(company.getCreatedDate())
            .lastModifiedDate(company.getLastModifiedDate())
            .build();
    }

}
