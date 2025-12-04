import React, { useEffect, useState } from 'react';
import api from '@/api/axios';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { useNavigate } from 'react-router-dom';

export default function DashboardPage() {
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await api.get('/api/hello');
                setMessage(response.data.message);
            } catch (err) {
                if (err.response && (err.response.status === 401 || err.response.status === 403)) {
                    navigate('/login');
                }
            }
        };
        fetchData();
    }, [navigate]);

    const handleLogout = () => {
        localStorage.removeItem('jwt_token');
        navigate('/login');
    };

    return (
        <div className="min-h-screen bg-background p-8">
            <div className="max-w-4xl mx-auto space-y-6">
                <div className="flex justify-between items-center">
                    <h1 className="text-3xl font-bold tracking-tight">Dashboard</h1>
                    <Button variant="outline" onClick={handleLogout}>Logout</Button>
                </div>
                <Card>
                    <CardHeader>
                        <CardTitle>Protected Resource</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <p className="text-lg">{message || 'Loading...'}</p>
                    </CardContent>
                </Card>
            </div>
        </div>
    );
}
