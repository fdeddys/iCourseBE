

INSERT INTO public.m_role_user(
	role_id, user_id, created_at, updated_at, created_by, updated_by, status)
	VALUES (1, 2, '2018-09-11', '2018-09-11', 2, 2, 1);


INSERT INTO public.m_role_menu(
	role_id, menu_id, status, created_at, updated_at, created_by, updated_by)
	VALUES (1,2, 1, '2018-09-11', '2018-09-11', 2, 2);

insert into m_roles(id, created_at, updated_at, description, name, created_by, updated_by)
values
(1, '2018-09-11','2018-09-11','admin','admin',2,2);









INSERT INTO public.m_menus(
	id, name, description, created_at, updated_at, created_by, updated_by, parent_id, icon, status)
	VALUES (1, 'transaksi', 'Transaksi', '2018-09-11', '2018-09-11', 2, 2, 0, 'settings', 1);


INSERT INTO public.m_menus(
	id, name, description, created_at, updated_at, created_by, updated_by, link, parent_id, icon, status)
	VALUES (3, 'outlet', 'Outlet', '2018-09-11', '2018-09-11', 2, 2, 'outlet',  1, 'person', 1);

INSERT INTO public.m_role_menu(
	role_id, menu_id, status, created_at, updated_at, created_by, updated_by)
	VALUES (1,3, 1, '2018-09-11', '2018-09-11', 2, 2);

INSERT INTO public.m_menus(
	id, name, description, created_at, updated_at, created_by, updated_by, link, parent_id, icon, status)
	VALUES (4, 'group-outlet', 'Group Outlet', '2018-09-11', '2018-09-11', 2, 2, 'group-outlet',  1, 'person', 1);



INSERT INTO public.m_role_menu(
	role_id, menu_id, status, created_at, updated_at, created_by, updated_by)
	VALUES (1,4, 1, '2018-09-11', '2018-09-11', 2, 2);


INSERT INTO public.m_menus(
	id, name, description, created_at, updated_at, created_by, updated_by, link, parent_id, icon, status)
	VALUES (5, 'room', 'Room', '2018-09-11', '2018-09-11', 2, 2, 'room',  1, 'person', 1);

INSERT INTO public.m_role_menu(
	role_id, menu_id, status, created_at, updated_at, created_by, updated_by)
	VALUES (1,5, 1, '2018-09-11', '2018-09-11', 2, 2);

INSERT INTO public.m_menus(
	id, name, description, created_at, updated_at, created_by, updated_by, link, parent_id, icon, status)
	VALUES (6, 'classes', 'Class', '2018-09-11', '2018-09-11', 2, 2, 'classes',  1, 'person', 1);

INSERT INTO public.m_role_menu(
	role_id, menu_id, status, created_at, updated_at, created_by, updated_by)
	VALUES (1,6, 1, '2018-09-11', '2018-09-11', 2, 2);


INSERT INTO public.m_menus(
	id, name, description, created_at, updated_at, created_by, updated_by, link, parent_id, icon, status)
	VALUES (7, 'student', 'Student', '2018-09-11', '2018-09-11', 2, 2, 'student',  1, 'person', 1);

INSERT INTO public.m_role_menu(
	role_id, menu_id, status, created_at, updated_at, created_by, updated_by)
	VALUES (1,7, 1, '2018-09-11', '2018-09-11', 2, 2);

INSERT INTO public.m_menus(
	id, name, description, created_at, updated_at, created_by, updated_by, link, parent_id, icon, status)
	VALUES (8, 'teacher', 'Teacher', '2018-09-11', '2018-09-11', 2, 2, 'teacher',  1, 'person', 1);

INSERT INTO public.m_role_menu(
	role_id, menu_id, status, created_at, updated_at, created_by, updated_by)
	VALUES (1,8, 1, '2018-09-11', '2018-09-11', 2, 2);



INSERT INTO public.m_menus(
	id, name, description, created_at, updated_at, created_by, updated_by, link, parent_id, icon, status)
	VALUES (10, 'registration', 'Registration', '2018-09-11', '2018-09-11', 2, 2, 'registration',  2, 'person', 1);

INSERT INTO public.m_role_menu(
	role_id, menu_id, status, created_at, updated_at, created_by, updated_by)
	VALUES (1,10, 1, '2018-09-11', '2018-09-11', 2, 2);



INSERT INTO public.m_menus(
	id, name, description, created_at, updated_at, created_by, updated_by, link, parent_id, icon, status)
	VALUES (11, 'payment', 'Payment', '2018-09-11', '2018-09-11', 2, 2, 'payment',  2, 'person', 1);

INSERT INTO public.m_role_menu(
	role_id, menu_id, status, created_at, updated_at, created_by, updated_by)
	VALUES (1,11, 1, '2018-09-11', '2018-09-11', 2, 2);




INSERT INTO public.m_role_menu(
	role_id, menu_id, status, created_at, updated_at, created_by, updated_by)
	VALUES (1,12, 1, '2018-09-11', '2018-09-11', 2, 2);


INSERT INTO public.m_menus(
	id, name, description, created_at, updated_at, created_by, updated_by, link, parent_id, icon, status)
	VALUES (13, 'report-form', 'Report', '2018-09-11', '2018-09-11', 2, 2, 'report',  12, 'person', 1);

INSERT INTO public.m_role_menu(
	role_id, menu_id, status, created_at, updated_at, created_by, updated_by)
	VALUES (1,13, 1, '2018-09-11', '2018-09-11', 2, 2);


INSERT INTO public.m_menus(
	id, name, description, created_at, updated_at, created_by, updated_by, link, parent_id, icon, status)
	VALUES (14, 'util', 'Utility', '2018-09-11', '2018-09-11', 2, 2, null,  0, 'person', 1);

INSERT INTO public.m_role_menu(
	role_id, menu_id, status, created_at, updated_at, created_by, updated_by)
	VALUES (1,14, 1, '2018-09-11', '2018-09-11', 2, 2);


INSERT INTO public.m_menus(
	id, name, description, created_at, updated_at, created_by, updated_by, link, parent_id, icon, status)
	VALUES (15, 'user', 'User', '2018-09-11', '2018-09-11', 2, 2, 'user',  14, 'person', 1);

INSERT INTO public.m_role_menu(
	role_id, menu_id, status, created_at, updated_at, created_by, updated_by)
	VALUES (1,15, 1, '2018-09-11', '2018-09-11', 2, 2);


INSERT INTO public.m_menus(
	id, name, description, created_at, updated_at, created_by, updated_by, link, parent_id, icon, status)
	VALUES (16, 'role', 'Role', '2018-09-11', '2018-09-11', 2, 2, 'role',  14, 'person', 1);

INSERT INTO public.m_role_menu(
	role_id, menu_id, status, created_at, updated_at, created_by, updated_by)
	VALUES (1,16, 1, '2018-09-11', '2018-09-11', 2, 2);
