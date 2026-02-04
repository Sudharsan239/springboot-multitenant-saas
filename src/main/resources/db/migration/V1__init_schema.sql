-- 1. Organizations Table (The Tenant)
CREATE TABLE organizations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created_at DATETIME(6),
    modified_at DATETIME(6),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- 2. Users Table
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    organization_id BIGINT,
    created_at DATETIME(6),
    modified_at DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_user_organization FOREIGN KEY (organization_id) REFERENCES organizations (id)
) ENGINE=InnoDB;

-- 3. Projects Table
CREATE TABLE projects (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    organization_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_project_organization FOREIGN KEY (organization_id) REFERENCES organizations (id)
) ENGINE=InnoDB;

-- 4. Tasks Table
CREATE TABLE tasks (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    status VARCHAR(50),
    assigned_to_id BIGINT,
    project_id BIGINT,
    organization_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_task_user FOREIGN KEY (assigned_to_id) REFERENCES users (id),
    CONSTRAINT fk_task_project FOREIGN KEY (project_id) REFERENCES projects (id),
    CONSTRAINT fk_task_organization FOREIGN KEY (organization_id) REFERENCES organizations (id)
) ENGINE=InnoDB;

-- Indexes for performance and multi-tenancy
CREATE INDEX idx_user_org ON users(organization_id);
CREATE INDEX idx_project_org ON projects(organization_id);
CREATE INDEX idx_task_org ON tasks(organization_id);