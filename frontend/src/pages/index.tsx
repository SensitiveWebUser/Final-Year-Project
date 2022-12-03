import React from 'react';
import { useTranslation } from 'react-i18next';

const Home: React.FC = () => {
	const { t } = useTranslation();
	return (
		<div
			style={{
				display: 'flex',
				flexDirection: 'column',
				minHeight: '100vh',
			}}
		>
			<h1>{t('translation.title')}</h1>
		</div>
	);
};

export default Home;
