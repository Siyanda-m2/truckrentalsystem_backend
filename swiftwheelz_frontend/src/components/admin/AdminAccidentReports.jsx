import React, { useState, useEffect } from 'react';
import axios from 'axios';

// Replace with your base API URL
const API_BASE_URL = 'http://localhost:8080/swiftwheelzdb/accidentReport';

const AdminAccidentReports = () => {
    const [accidentReports, setAccidentReports] = useState([]);
    const [responseText, setResponseText] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Fetch all accident reports when the component loads
    useEffect(() => {
        const fetchReports = async () => {
            try {
                const result = await axios.get(`${API_BASE_URL}/getAll`);
                setAccidentReports(result.data);
                setLoading(false);
            } catch (error) {
                setError('Error fetching accident reports');
                setLoading(false);
            }
        };

        fetchReports();
    }, []);

    // Handle response text change for a specific report
    const handleResponseChange = (reportId, value) => {
        setResponseText({
            ...responseText,
            [reportId]: value
        });
    };

    // Handle admin response submission
    const handleResponseSubmit = async (reportId) => {
        try {
            const updatedReport = {
                ...accidentReports.find((report) => report.reportId === reportId),
                adminResponse: responseText[reportId] // Assuming the backend supports this field
            };

            await axios.put(`${API_BASE_URL}/update/${reportId}`, updatedReport);
            alert('Response submitted successfully.');
        } catch (error) {
            alert('Error submitting response.');
        }
    };

    // Inline CSS styles
    const styles = {
        container: {
            marginTop: '40px',
            padding: '20px',
            backgroundColor: '#f8f9fa',
            borderRadius: '8px',
        },
        heading: {
            fontSize: '24px',
            fontWeight: 'bold',
            marginBottom: '20px',
            textAlign: 'center',
            color: '#343a40',
        },
        table: {
            width: '100%',
            borderCollapse: 'collapse',
            marginBottom: '20px',
        },
        th: {
            border: '1px solid #dee2e6',
            padding: '12px',
            backgroundColor: '#e9ecef',
            textAlign: 'left',
        },
        td: {
            border: '1px solid #dee2e6',
            padding: '12px',
        },
        textarea: {
            width: '100%',
            padding: '10px',
            borderRadius: '5px',
            border: '1px solid #ced4da',
            marginBottom: '10px',
        },
        submitButton: {
            backgroundColor: '#007bff',
            color: '#fff',
            border: 'none',
            padding: '8px 16px',
            borderRadius: '5px',
            cursor: 'pointer',
        },
        loadingText: {
            fontSize: '18px',
            textAlign: 'center',
            marginTop: '20px',
        },
        errorText: {
            color: 'red',
            fontSize: '16px',
            textAlign: 'center',
        },
    };

    if (loading) return <p style={styles.loadingText}>Loading reports...</p>;
    if (error) return <p style={styles.errorText}>{error}</p>;

    return (
        <div style={styles.container}>
            <h2 style={styles.heading}>Customer Accident Reports</h2>
            <table style={styles.table}>
                <thead>
                <tr>
                    <th style={styles.th}>Report ID</th>
                    <th style={styles.th}>Customer ID</th>
                    <th style={styles.th}>Accident Date</th>
                    <th style={styles.th}>Description</th>
                    <th style={styles.th}>Location</th>
                    <th style={styles.th}>Damage Cost</th>
                    <th style={styles.th}>Truck ID</th>
                    <th style={styles.th}>Admin Response</th>
                </tr>
                </thead>
                <tbody>
                {accidentReports.map((report) => (
                    <tr key={report.reportId}>
                        <td style={styles.td}>{report.reportId}</td>
                        <td style={styles.td}>{report.customer.customerId}</td>
                        <td style={styles.td}>{report.accidentDate}</td>
                        <td style={styles.td}>{report.description}</td>
                        <td style={styles.td}>{report.location}</td>
                        <td style={styles.td}>{report.damageCost}</td>
                        <td style={styles.td}>{report.truck.vin}</td>
                        <td style={styles.td}>
                <textarea
                    style={styles.textarea}
                    rows="3"
                    placeholder="Enter your response"
                    value={responseText[report.reportId] || ''}
                    onChange={(e) =>
                        handleResponseChange(report.reportId, e.target.value)
                    }
                />
                            <button
                                style={styles.submitButton}
                                onClick={() => handleResponseSubmit(report.reportId)}
                            >
                                Submit Response
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default AdminAccidentReports;