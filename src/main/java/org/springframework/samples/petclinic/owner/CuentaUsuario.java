/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.core.style.ToStringCreator;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.util.Assert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "cuenta")
public class CuentaUsuario extends Person {

	@Id
	@Unique
	private String userName;

	@Column(name = "password")
	@NotEmpty
	@Unique
	private String password;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM-yyyy'T'HH:mm:ss.SSSX")
	private Date created;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

}
